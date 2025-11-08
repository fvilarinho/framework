package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.exceptions.InternalErrorException;

import java.io.Serial;

/**
 * Class that defines the option of the list component.
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
public class ListOptionPropertyComponent extends BaseOptionPropertyComponent{
    @Serial
    private static final long serialVersionUID = 5718731968797128195L;

    @Override
    protected void renderSelectionAttribute() throws InternalErrorException{
        boolean selected = isSelected();
        
        if(selected)
            print(" selected");
    }

    @Override
    protected void renderLabel() throws InternalErrorException{
        print(getLabel());
    }

    @Override
    protected void renderType() throws InternalErrorException{
    }

    @Override
    protected void renderName() throws InternalErrorException{
    }

    @Override
    protected void renderId() throws InternalErrorException{
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        print("<option");
        
        renderAttributes();
        
        print(">");
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        renderLabel();
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        println("</option>");
    }
}