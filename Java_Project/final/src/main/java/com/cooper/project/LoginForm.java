package com.cooper.project;

import java.util.ArrayList;
import java.util.List;

import com.cooper.project.domain.User;

import lombok.Data;

@Data
public class LoginForm {

    private List<User> users = new ArrayList<>();
}
