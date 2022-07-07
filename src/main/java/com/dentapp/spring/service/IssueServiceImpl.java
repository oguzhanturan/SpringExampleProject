package com.dentapp.spring.service;

import com.dentapp.spring.models.Auth.ERole;
import com.dentapp.spring.models.Auth.User;
import com.dentapp.spring.models.*;
import com.dentapp.spring.payload.request.CreateIssueRequest;
import com.dentapp.spring.payload.request.GetIssueRequest;
import com.dentapp.spring.payload.request.UpdateIssueRequest;
import com.dentapp.spring.payload.response.*;
import com.dentapp.spring.repository.Auth.UserRepository;
import com.dentapp.spring.repository.*;
import com.dentapp.spring.security.services.UserDetailsImpl;
import com.dentapp.spring.util.IssueUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IssueServiceImpl implements IssueService {

    private final ModelMapper mapper;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final DoctorRepository doctorRepository;
    private final NoteRepository noteRepository;
    private final PatientTreatmentRepository patientTreatmentRepository;

    @Autowired
    public IssueServiceImpl(final IssueRepository issueRepository,
                            final UserRepository userRepository,
                            final IssueTypeRepository issueTypeRepository,
                            final PatientRepository patientRepository,
                            final DoctorRepository doctorRepository,
                            final NoteRepository noteRepository,
                            final PatientTreatmentRepository patientTreatmentRepository,
                            final ModelMapper mapper) {
        this.issueRepository = issueRepository;
        this.issueTypeRepository = issueTypeRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.noteRepository = noteRepository;
        this.patientTreatmentRepository = patientTreatmentRepository;
        this.mapper = mapper;
    }

    @Override
    public CreateIssueResponse createIssue(CreateIssueRequest request) {

        String uuid = IssueUtil.generateUuid(issueRepository);

        User userTech = userRepository.findById(Long.parseLong(request.getUserId())).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with id"));

        Doctor doctor = doctorRepository.findById(Long.parseLong(request.getDoctor())).orElseGet(
                (Supplier<? extends Doctor>) () -> {
                    Doctor doctor1 = Doctor.builder().name(request.getName()).build();
                    doctorRepository.saveAndFlush(doctor1);
                    return doctor1;
                }
        );
        // TODO patient ilk sefer oluşturulduğunda onun için bir randevu tarihi oluşturmak gerekecek
        Patient patientTemp = new Patient();
        Optional<Patient> patient = patientRepository.findByFullName(request.getPatientName());

        if (patient.isPresent()) {
            LocalDateTime appointmentDate = null;
            if (!request.getPatientTreatment().getAppointmentDate().isEmpty()) {
                appointmentDate = LocalDateTime.parse(request.getPatientTreatment().getAppointmentDate());  //Randevu Tarihi
            }
            PatientTreatment patientTreatment = PatientTreatment.builder()
                    .endTreatmentDate(LocalDateTime.parse(request.getPatientTreatment().getEndTreatmentDate()))
                    .appointmentDate(appointmentDate)
                    .build();
            patientTreatmentRepository.save(patientTreatment);
            patient.get().getPatientTreatment().add(patientTreatment);
            patient.get().setPatientTreatment(patient.get().getPatientTreatment());
            patientTemp = patient.get();
        } else {
            LocalDateTime appointmentDate = null;
            if (!request.getPatientTreatment().getAppointmentDate().isEmpty()) {
                appointmentDate = LocalDateTime.parse(request.getPatientTreatment().getAppointmentDate());  //Randevu Tarihi
            }
            PatientTreatment patientTreatment = PatientTreatment.builder()
                    .endTreatmentDate(LocalDateTime.parse(request.getPatientTreatment().getEndTreatmentDate()))
                    .appointmentDate(appointmentDate)
                    .build();
            patientTreatmentRepository.save(patientTreatment);

            Patient patient1 = Patient.builder()
                    .fullName(request.getPatientName())
                    .user(userTech)
                    .patientTreatment(List.of(patientTreatment))
                    .build();

            patientRepository.save(patient1);
            patientTemp = patient1;
        }

        final IssueType issueType = issueTypeRepository.findById(Long.parseLong(request.getIssueTypeId())).get();
        User userCreated = userRepository.findById(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with id"));

        Note clinicNote = Note.builder().message(request.getClinicNote()).build();
        noteRepository.save(clinicNote);

        Issue issue = Issue.builder()
                .issueId(uuid)
                .issueType(issueType)
                .doctor(doctor)
                .patient(patientTemp)//Hasta Bilgileri
                .user(userCreated)//Kaydı Oluşturan user
                .statusType(StatusType.waiting)
                .clinicNote(List.of(clinicNote))
                .startDate(LocalDateTime.parse(request.getStartDate()))//Veriliş Tarihi
                .possibleEndDate(LocalDateTime.parse(request.getPossibleEndDate()))//İstenen Tarih
                .name(request.getName()).build();

        issueRepository.save(issue);

        return new CreateIssueResponse(String.valueOf(issue.getId()));
    }

    @Override
    public UpdateIssueResponse updateIssue(UpdateIssueRequest request) {
        // update yeni kayıt atıyor.
        // TODO delay veya onaylandı durumundan waiting e geçerse end date i - null yapıcaz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        Issue issue = issueRepository.findById(Long.parseLong(request.getIssueId()))
                .orElseThrow(() -> new RuntimeException("Error: Issue is not found."));

        Patient patient = issue.getPatient();
        User technician = issue.getPatient().getUser();
        PatientTreatment patientTreatment = new PatientTreatment();
        /*final PatientTreatment patientTreatment = issue.getPatient().getPatientTreatment().stream().filter(
                p -> request.getPatientTreatment().getId().equals(p.getId())
        ).collect(Collectors.toList()).get(0);*/

        if (roles.stream().findFirst().get().equals(ERole.ROLE_ADMIN.toString()) || roles.stream().findFirst().get().equals(ERole.ROLE_MODERATOR.toString())) {
            if (request.getPatientTreatment() != null) {
                patientTreatment = patientTreatmentRepository.findById(Long.parseLong(
                        request.getPatientTreatment().getId())).orElse(new PatientTreatment());
            }

            if (request.getStatus() != null && StatusType.valueOf(request.getStatus()).equals(StatusType.completed)) {
                issue.setStatusType(StatusType.completed);
            } else if (request.getStatus() != null && StatusType.valueOf(request.getStatus()).equals(StatusType.waiting)) {

                if (request.getCurrentStatus() != null && StatusType.valueOf(request.getCurrentStatus()).equals(StatusType.review)) {
                    technician = userRepository.findById(Long.parseLong(request.getTechnicianId())).get();


                    if (!request.getClinicNote().isEmpty()) {
                        Note clinicNote = Note.builder().message(request.getClinicNote()).build();
                        noteRepository.save(clinicNote);
                        List<Note> temp = issue.getClinicNote();
                        temp.add(clinicNote);
                        issue.setClinicNote(temp);
                    }
                    if (request.getPatientTreatment().getAppointmentDate() != null && !request.getPatientTreatment().getAppointmentDate().isEmpty()) {

                        patientTreatment.setAppointmentDate(LocalDateTime.parse(request.getPatientTreatment().getAppointmentDate()));
                        patientTreatment.setEndTreatmentDate(LocalDateTime.parse(request.getPatientTreatment().getEndTreatmentDate()));
                        //patient.getPatientTreatment().add(patientTreatment);
                        patient.setUser(technician);
                        patient.setPatientTreatment(patient.getPatientTreatment()); //Randevu Tarihi
                        issue.setPatient(patient);
                    } else {

                        List<PatientTreatment> newList = patient.getPatientTreatment();
                        PatientTreatment finalPatientTreatment3 = patientTreatment;
                        newList.forEach(patientTreatment1 -> {
                            if (patientTreatment1.getId().equals(finalPatientTreatment3.getId())) {
                                patientTreatment1.setAppointmentDate(null);
                            }
                        });

                        patient.setPatientTreatment(newList); //Randevu Tarihi
                        issue.setPatient(patient);
                    }

                    issue.setPossibleEndDate(LocalDateTime.parse(request.getPossibleEndDate()));
                    List<PatientTreatment> newList = patient.getPatientTreatment();
                    PatientTreatment finalPatientTreatment2 = patientTreatment;
                    newList.forEach(patientTreatment1 -> {
                        if (patientTreatment1.getId().equals(finalPatientTreatment2.getId())) {
                            patientTreatment1.setEndTreatmentDate(LocalDateTime.parse(request.getPatientTreatment().getEndTreatmentDate()));
                        }
                    });

                    patient.setPatientTreatment(newList);
                    issue.setStatusType(StatusType.waiting);
                    issue.setCurrentStatus(null);
                } else {
                    issue.setStatusType(StatusType.waiting);
                    if (!request.getClinicNote().isEmpty()) {
                        Note clinicNote = Note.builder().message(request.getClinicNote()).build();
                        noteRepository.save(clinicNote);
                        List<Note> temp = issue.getClinicNote();
                        temp.add(clinicNote);
                        issue.setClinicNote(temp);
                    }
                    issue.setEndDate(null);
                }

            } else if (request.getStatus() != null && StatusType.valueOf(request.getStatus()).equals(StatusType.approved)) {
                issue.setStatusType(StatusType.approved); //Durum
            } else if (request.getPatientTreatment().getAppointmentDate() != null && !request.getPatientTreatment().getAppointmentDate().toString().isEmpty()) {
                List<PatientTreatment> newList = patient.getPatientTreatment();
                PatientTreatment finalPatientTreatment1 = patientTreatment;
                newList.forEach(patientTreatment1 -> {
                    if (patientTreatment1.getId().equals(finalPatientTreatment1.getId())) {
                        patientTreatment1.setAppointmentDate(LocalDateTime.parse(request.getPatientTreatment().getAppointmentDate()));//Randevu Tarihi
                    }
                });

                patient.setPatientTreatment(newList);//Randevu Tarihi
                issue.setPatient(patient);
            } else if (!request.getDoctorId().isEmpty()) {
                Doctor doctor = doctorRepository.findById(Long.parseLong(request.getDoctorId())).orElseThrow(
                        () -> new UsernameNotFoundException("Doctor Not Found with id"));
                issue.setDoctor(doctor);
            } else {
                issue.setStatusType(StatusType.valueOf(request.getStatus())); //Durum
                issue.setPossibleEndDate(LocalDateTime.parse(request.getPossibleEndDate())); //İstenen Tarih

                if (!request.getClinicNote().isEmpty()) {
                    Note clinicNote = Note.builder().message(request.getClinicNote()).build();
                    issue.setClinicNote(List.of(clinicNote));
                }

                issue.setName(request.getName());
                patient.setFullName(request.getName());
                List<PatientTreatment> newList = patient.getPatientTreatment();
                PatientTreatment finalPatientTreatment = patientTreatment;
                newList.forEach(patientTreatment1 -> {
                    if (patientTreatment1.getId().equals(finalPatientTreatment.getId())) {
                        patientTreatment1.setAppointmentDate(LocalDateTime.parse(request.getPatientTreatment().getAppointmentDate()));//Randevu Tarihi
                        patientTreatment1.setEndTreatmentDate(LocalDateTime.parse(request.getPatientTreatment().getEndTreatmentDate()));//Tedavi son tarihi
                    }
                });

                patient.setPatientTreatment(newList);

                patientRepository.save(patient);
                issue.setPatient(patient); //Hasta Bilgileri
            }

        } else {

            if (request.getStatus() != null && StatusType.valueOf(request.getStatus()).equals(StatusType.accepted)) {
                issue.setStatusType(StatusType.valueOf(request.getStatus()));
                issue.setAgreementTechnician(true);
            }
            if (request.getStatus() != null && StatusType.valueOf(request.getStatus()).equals(StatusType.notApproved)) {
                if (!request.getTechnicianNote().isEmpty()) {
                    Note technicianNote = Note.builder().message(request.getTechnicianNote()).build();
                    noteRepository.save(technicianNote);
                    List<Note> temp = issue.getTechnicianNote();
                    temp.add(technicianNote);
                    issue.setTechnicianNote(temp);
                }

                if (!request.getEndDate().isEmpty()) {
                    issue.setEndDate(LocalDateTime.parse(request.getEndDate()));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Teslim tarihi boş girilemez");
                }
                issue.setStatusType(StatusType.notApproved);
            }
            if (request.getStatus() != null && StatusType.valueOf(request.getStatus()).equals(StatusType.review)) {
                issue.setStatusType(StatusType.review); //Durum
            }
            if (request.getStatus() != null && StatusType.valueOf(request.getStatus()).equals(StatusType.approved)) {
                issue.setStatusType(StatusType.approved);
            }
        }

        Issue save = issueRepository.save(issue);
        issueRepository.flush();
        return new UpdateIssueResponse(String.valueOf(save.getId()));
    }

    @Override
    public GetAllIssueResponse getAllIssue() {
        List<Issue> issueList = issueRepository.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
        GetAllIssueResponse response = new GetAllIssueResponse();
        response.setIssueList(issueList);
        return response;
    }

    @Override
    public GetIssueResponse getIssueById(GetIssueRequest request) {
        GetIssueResponse response = new GetIssueResponse();
        issueRepository.findById(request.getIssueId()).ifPresent(response::setIssue);
        return response;
    }

    @Override
    public SuccessResponse deleteIssueById(Long id) {
        var delete = issueRepository.findById(id);
        delete.ifPresent(issueRepository::delete);
        return new SuccessResponse("success");
    }

    @Override
    public ResponseEntity<List<IssueType>> getIssueTypes() {
        return new ResponseEntity<>(issueTypeRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> setIssueType(String issueType) {
        var issueType1 = issueTypeRepository.findByName(issueType);
        if (issueType1.isPresent()) {
            issueType1.get().setName(issueType);
            issueTypeRepository.save(issueType1.get());
            return new ResponseEntity<>(issueType1.get().getId(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Cannot find issue type!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public GetAllIssueResponse getIssueByType(String type) {
        //issueRepository.findByIssueType_();
        List<Issue> byStatusType_ = issueRepository.findByStatusType_(StatusType.valueOf(type));
        GetAllIssueResponse response = new GetAllIssueResponse();
        response.setIssueList(byStatusType_);
        return response;
    }

    @Override
    public GetAllIssueResponse getAllByPatientId(String fullName) {
        List<Issue> issueList = issueRepository.findAllByPatientId_FullNameOrderByEndDateDesc(fullName);
        GetAllIssueResponse response = new GetAllIssueResponse();
        response.setIssueList(issueList);
        return response;
    }
}
