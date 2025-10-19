package br.com.concepting.framework.model;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;

@Model
public class TestModel extends BaseModel {
    @Property
    private String testField;

    public String getTestField() {
        return testField;
    }
    public void setTestField(String testField) {
        this.testField = testField;
    }
}
