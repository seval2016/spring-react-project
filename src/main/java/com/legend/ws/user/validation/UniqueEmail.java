package com.legend.ws.user.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;



/*
 ************    Kendi constraintimizi oluşturma      ************

1.Adım : @Interface oluşturalım (UniqueEmail) ve bu etiiketleme ile bu isimli anotation kullanacağız diye bildiririz.

2.Adım : Aşağıdaki üç anotation'u ekle  ve kendi fieldlarına göre düzenle
    @Constraint(validatedBy = { })
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)

3.Adım : Üç ayrı değişkeni ekleyelim ve uyarlayalım

	String message() default "{jakarta.validation.constraints.NotBlank.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

4.Adım :UniqueEmailValidator.class sayfasını oluştur (Bu oluşturacağımız constraint'e göre ismi değişebilir)


5.Adım: userRepository injektion yap 

6.Adım :userRepository içerisinde doğru query yaz

7.Adım : Validator classında aşağıdaki kod ile var olup olmadığını kontrol et
 User inDB=userRepository.findByEmail(value);
        return inDB==null;
    }

  8.Adım : User controller'da  @valid annotation ile bir validation gerçekleşiyor. 
 Bir sonraki adım da  User service'den save metodundan sonra ıkıncı bir validation geçiriyor ki bu gereksiz bir durum.
 Bu durumu disable ediyoruz bunun için aşağıdaki işlemi gerçekleştiriyoruz.

 spring.jpa.properties.javax.persistence.validation.mode=NONE;  

 */

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ ElementType.FIELD }) // @UnigueEmail ismi ile kullanabilmek için field tanımlaması
@Retention(RetentionPolicy.RUNTIME) // hangi aşamalarda kullanılacağı
public @interface UniqueEmail {

    String message() default "Email already in Use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
