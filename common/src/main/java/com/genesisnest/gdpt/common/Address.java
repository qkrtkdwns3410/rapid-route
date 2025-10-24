package com.genesisnest.gdpt.common;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public static Address of(String street, String city, String state, String zipCode, String country) {
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("도로명 주소는 필수입니다.");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("도시는 필수입니다.");
        }
        return new Address(street, city, state, zipCode, country);
    }

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(street);
        if (city != null)
            sb.append(", ").append(city);
        if (state != null)
            sb.append(", ").append(state);
        if (zipCode != null)
            sb.append(" ").append(zipCode);
        if (country != null)
            sb.append(", ").append(country);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(city, address.city) &&
                Objects.equals(state, address.state) &&
                Objects.equals(zipCode, address.zipCode) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, state, zipCode, country);
    }

    @Override
    public String toString() {
        return getFullAddress();
    }
}