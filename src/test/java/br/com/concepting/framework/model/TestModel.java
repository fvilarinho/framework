package br.com.concepting.framework.model;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;

@Model
public class TestModel extends BaseModel {
    @Property
    private String testField = null;

    public TestModel() {
        super();
    }

    public TestModel(String testField) {
        this();

        setTestField(testField);
    }

    public String getTestField() {
        return testField;
    }
    public void setTestField(String testField) {
        this.testField = testField;
    }
    private void sayHello(){
        System.out.println("Hello world!");
    }

    public static final void saySomething(String something) {
        System.out.println(something);
    }

    @Override
    public String toString(){
        return getTestField();
    }
}
