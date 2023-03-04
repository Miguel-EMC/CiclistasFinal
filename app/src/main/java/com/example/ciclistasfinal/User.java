package com.example.ciclistasfinal;

public class User {
    public String name, last_name, email, password, address, rol;

    public User(String name, String last_name, String email, String password, String address, String rol) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.rol = rol;
    }

    public User() {}
    public String getName() {
        return name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getRol() {
        return rol;
    }
}
