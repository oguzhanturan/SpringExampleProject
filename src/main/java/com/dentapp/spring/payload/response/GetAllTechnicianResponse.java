package com.dentapp.spring.payload.response;

import com.dentapp.spring.models.Auth.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllTechnicianResponse implements Serializable {
   private List<User> users;
}
