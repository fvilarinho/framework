package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.types.AlignmentType;

/**
 * Class that defines the grid column group component.
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
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
 */
public class GridColumnGroupComponent extends GridColumnComponent{
    private static final long serialVersionUID = 6723734982419563034L;
    
    private boolean aggregate = false;
    
    /**
     * Indicates if the column is for aggregation.
     *
     * @return True/False.
     */
    public boolean aggregate(){
        return this.aggregate;
    }
    
    /**
     * Defines if the column is for aggregation.
     *
     * @param aggregate True/False.
     */
    public void setAggregate(boolean aggregate){
        this.aggregate = aggregate;
    }

    @Override
    protected void buildName() throws InternalErrorException{
    }

    @Override
    protected void buildAlignment() throws InternalErrorException{
        AlignmentType alignment = getAlignmentType();
        
        if(alignment == null){
            if(!this.aggregate)
                alignment = AlignmentType.CENTER;
            else
                alignment = AlignmentType.LEFT;
            
            setAlignmentType(alignment);
        }
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setAggregate(false);
    }
}