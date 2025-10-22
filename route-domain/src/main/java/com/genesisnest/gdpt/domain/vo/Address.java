package com.genesisnest.gdpt.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    
    public String getFullAddress() {
        return String.format("%s, %s %s %s", street, city, state, postalCode);
    }
    
    public static Address of(String street, String city, String state, String postalCode) {
        return Address.builder()
                .street(street)
                .city(city)
                .state(state)
                .postalCode(postalCode)
                .country("KR")
                .build();
    }
    
    public static Address of(String street, String city, String state, String postalCode, String country) {
        return Address.builder()
                .street(street)
                .city(city)
                .state(state)
                .postalCode(postalCode)
                .country(country)
                .build();
    }
    
    public static Address seoul(String street, String postalCode) {
        return of(street, "서울특별시", "서울", postalCode);
    }
    
    public static Address busan(String street, String postalCode) {
        return of(street, "부산광역시", "부산", postalCode);
    }
    
    public static Address incheon(String street, String postalCode) {
        return of(street, "인천광역시", "인천", postalCode);
    }
}
