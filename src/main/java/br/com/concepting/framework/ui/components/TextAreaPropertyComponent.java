package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.types.ComponentType;

import java.io.Serial;

/**
 * Class that defines the text area component.
 *
 * @author fvilarinho
 * @since 1.0.0
 *
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public class TextAreaPropertyComponent extends TextPropertyComponent{
    @Serial
    private static final long serialVersionUID = -4412598905102532413L;
    
    private int rows = 0;
    private int columns = 0;
    
    /**
     * Returns the number of columns.
     *
     * @return Numeric value that contains the number of columns.
     */
    public int getColumns(){
        return this.columns;
    }
    
    /**
     * Defines the number of columns.
     *
     * @param columns Numeric value that contains the number of columns.
     */
    public void setColumns(int columns){
        this.columns = columns;
    }
    
    /**
     * Returns the number of rows.
     *
     * @return Numeric value that contains the number of rows.
     */
    public int getRows(){
        return this.rows;
    }
    
    /**
     * Defines the number of rows.
     *
     * @param rows Numeric value that contains the number of rows.
     */
    public void setRows(int rows){
        this.rows = rows;
    }

    @Override
    protected void renderValue() throws InternalErrorException{
    }

    @Override
    protected void renderAttributes() throws InternalErrorException{
        super.renderAttributes();
        
        int rows = getRows();
        
        if(rows > 0){
            print(" rows=\"");
            print(rows);
            print("\"");
        }
        
        int columns = getColumns();
        
        if(columns > 0){
            print(" cols=\"");
            print(columns);
            print("\"");
        }
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.TEXT_AREA);
        
        super.initialize();
    }

    @Override
    protected void renderType() throws InternalErrorException{
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        super.renderOpen();
        
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(!hasInvalidDefinition || getValue() != null){
            print("<textarea");
            
            renderAttributes();
            
            print(">");
        }
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        String formattedValue = getFormattedValue();
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(!hasInvalidDefinition){
            if(formattedValue != null && !formattedValue.isEmpty())
                println(formattedValue);
        }
        else
            super.renderBody();
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        boolean hasInvalidDefinition = hasInvalidDefinition();
        
        if(!hasInvalidDefinition || getValue() != null)
            println("</textarea>");
        
        super.renderClose();
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setRows(0);
        setColumns(0);
    }
}