package br.com.concepting.framework.model;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;

@Model(descriptionPattern = "#{name} - #{phone}")
public class PhonebookModel extends BaseModel{
    @Property(isIdentity = true)
    private String name;

    @Property(isForSearch = true)
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
