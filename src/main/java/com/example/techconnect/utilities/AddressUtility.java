package com.example.techconnect.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor





public class AddressUtility {

    private String street;
    private String city;
    private String state;
    private String country;

    public AddressUtility(String street, String city, String state, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String concatenateAddress(String street, String city, String state, String country) {

        return street + ", " + city + ", " + state + ", " + country;


    }


}
