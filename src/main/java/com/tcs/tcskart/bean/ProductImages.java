package com.tcs.tcskart.bean;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImages {
    private String filename;
    private String contentType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="LONGBLOB")
    private byte[] data;
    public String getBase64() {
        return Base64.getEncoder().encodeToString(this.data);
    }
}

