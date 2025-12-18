package br.com.concepting.framework.ui.components;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ComponentType;

import jakarta.servlet.jsp.JspException;

import java.io.Serial;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class that defines the clock component.
 *
 * @author fvilarinho
 * @since 2.0.0
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
public class ClockComponent extends BaseComponent{
    @Serial
    private static final long serialVersionUID = 8103366607850798799L;
    
    private String pattern = null;
    private SimpleDateFormat formatter = null;
    
    /**
     * Defines the formatter of the clock.
     *
     * @param formatter Instance that contains the formatter.
     */
    private void setFormatter(SimpleDateFormat formatter){
        this.formatter = formatter;
    }
    
    /**
     * Returns the formatting pattern.
     *
     * @return String that contains the pattern.
     */
    public String getPattern(){
        return this.pattern;
    }
    
    /**
     * Defines the formatting pattern.
     *
     * @param pattern String that contains the pattern.
     */
    public void setPattern(String pattern){
        this.pattern = pattern;
    }

    @Override
    protected void initialize() throws InternalErrorException{
        setComponentType(ComponentType.CLOCK);
        
        Locale currentLanguage = getCurrentLanguage();
        
        if(this.pattern == null || this.pattern.isEmpty()){
            this.formatter = (SimpleDateFormat) DateFormat.getTimeInstance(DateFormat.DEFAULT, currentLanguage);
            this.pattern = this.formatter.toPattern();
            
            setPattern(this.pattern);
        }
        else
            this.formatter = new SimpleDateFormat(this.pattern, currentLanguage);
        
        super.initialize();
    }

    @Override
    protected void renderName() throws InternalErrorException{
    }
    
    /**
     * Renders the attribute that defines the formatting pattern.
     *
     * @throws InternalErrorException Occurs when was not possible to render.
     */
    protected void renderPatternAttribute() throws InternalErrorException{
        String name = getName();
        
        if(name == null || name.isEmpty())
            return;
        
        StringBuilder nameBuffer = new StringBuilder();
        
        nameBuffer.append(name);
        nameBuffer.append(".");
        nameBuffer.append(Constants.PATTERN_ATTRIBUTE_ID);
        
        HiddenPropertyComponent patternPropertyComponent = new HiddenPropertyComponent();
        
        patternPropertyComponent.setPageContext(this.pageContext);
        patternPropertyComponent.setOutputStream(getOutputStream());
        patternPropertyComponent.setName(nameBuffer.toString());
        patternPropertyComponent.setValue(getPattern());
        
        try{
            patternPropertyComponent.doStartTag();
            patternPropertyComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
    }

    @Override
    protected void renderOpen() throws InternalErrorException{
        renderPatternAttribute();
        
        print("<span");
        
        renderAttributes();
        
        println(">");
    }

    @Override
    protected void renderBody() throws InternalErrorException{
        println(this.formatter.format(new Date()));
    }

    @Override
    protected void renderClose() throws InternalErrorException{
        println("</span>");
        
        StringBuilder content = new StringBuilder();
        
        content.append("showClock();");
        content.append(StringUtil.getLineBreak());
        
        ScriptComponent scriptComponent = new ScriptComponent();
        
        scriptComponent.setPageContext(this.pageContext);
        scriptComponent.setOutputStream(getOutputStream());
        scriptComponent.setContent(content.toString());
        
        try{
            scriptComponent.doStartTag();
            scriptComponent.doEndTag();
        }
        catch(JspException e){
            throw new InternalErrorException(e);
        }
    }

    @Override
    protected void clearAttributes() throws InternalErrorException{
        super.clearAttributes();
        
        setPattern(null);
        setFormatter(null);
    }
}