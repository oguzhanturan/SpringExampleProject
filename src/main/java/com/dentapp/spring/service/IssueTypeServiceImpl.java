package com.dentapp.spring.service;

import com.dentapp.spring.models.IssueType;
import com.dentapp.spring.payload.request.CreateIssueTypeRequest;
import com.dentapp.spring.payload.request.UpdateIssueTypeRequest;
import com.dentapp.spring.payload.response.CreateIssueTypeResponse;
import com.dentapp.spring.payload.response.SuccessResponse;
import com.dentapp.spring.payload.response.UpdateIssueTypeResponse;
import com.dentapp.spring.repository.IssueTypeRepository;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IssueTypeServiceImpl implements IssueTypeService {

    private final ModelMapper mapper;
    private final IssueTypeRepository issueTypeRepository;

    public IssueTypeServiceImpl(ModelMapper mapper, IssueTypeRepository issueTypeRepository) {
        this.mapper = mapper;
        this.issueTypeRepository = issueTypeRepository;
    }

    @Override
    public CreateIssueTypeResponse createIssueType(CreateIssueTypeRequest request) {
        if (!issueTypeRepository.existsByName(request.getName())) {
            IssueType issueType = issueTypeRepository.save(mapper.map(request, IssueType.class));
            return mapper.map(issueType, CreateIssueTypeResponse.class);
        } else {
            throw new RuntimeException("Error: Exist Issue Type");
        }

    }

    @Override
    public UpdateIssueTypeResponse updateIssueType(UpdateIssueTypeRequest request) {
        Optional<IssueType> issueType = issueTypeRepository.findById(request.getId());

        if (issueType.isPresent()) {
            if (!issueType.get().getName().equals(request.getName())) {
                if (issueTypeRepository.existsByName(request.getName())) {
                    throw new RuntimeException("Error: Exist Issue Type");
                }
            }
            issueType.get().setName(request.getName());
            return mapper.map(issueTypeRepository.save(issueType.get()), UpdateIssueTypeResponse.class);
        } else {
            throw new RuntimeException("Error:Issue Type is not found.");
        }
    }

    @Override
    public SuccessResponse deleteIssueTypeById(Long id) {
        var delete = issueTypeRepository.findById(id);
        delete.ifPresent(issueTypeRepository::delete);
        return new SuccessResponse("success");
    }
}
