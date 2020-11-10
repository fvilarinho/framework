package br.com.concepting.framework.ui.components;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.ui.components.types.VisibilityType;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.ui.controller.UIController;
import br.com.concepting.framework.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ComponentType;

/**
 * Class that defines the accordion component.
 * 
 * @author fvilarinho
 * @since 3.0.0
 *
 * <pre>Copyright (C) 2007 Innovative Thinking. 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.</pre>
 */
public class AccordionComponent extends BaseActionFormComponent{
	private static final long serialVersionUID = 1642597166746306289L;

	private Boolean                multipleSelection  = null;
	private List<SectionComponent> sectionsComponents = null;
	private List<String>           currentSections    = null;

	/**
	 * Returns the list of sections.
	 * 
	 * @return List that contains the identifiers of the sections.
	 */
	protected List<String> getCurrentSections(){
		return this.currentSections;
	}

	/**
	 * Defines the list of sections.
	 * 
	 * @param currentSections List that contains the identifiers of the
	 * sections.
	 */
	private void setCurrentSections(List<String> currentSections){
		this.currentSections = currentSections;
	}

	/**
	 * Indicates if the component has multiple selection.
	 * 
	 * @return True/False.
	 */
	public Boolean hasMultipleSelection(){
		return this.multipleSelection;
	}

	/**
	 * Indicates if the component has multiple selection.
	 * 
	 * @return True/False.
	 */
	public Boolean getMultipleSelection(){
		return hasMultipleSelection();
	}

	/**
	 * Defines if the component has multiple selection.
	 * 
	 * @param multipleSelection True/False.
	 */
	public void setMultipleSelection(Boolean multipleSelection){
		this.multipleSelection = multipleSelection;
	}

	/**
	 * Returns the list of the sections.
	 * 
	 * @return Instance that contains the list of sections.
	 */
	protected List<SectionComponent> getSectionsComponents(){
		return this.sectionsComponents;
	}

	/**
	 * Defines the list of the sections.
	 * 
	 * @param sectionsComponents Instance that contains the list of sections.
	 */
	protected void setSectionsComponents(List<SectionComponent> sectionsComponents){
		this.sectionsComponents = sectionsComponents;
	}

	/**
	 * Adds a new section.
	 * 
	 * @param sectionComponent Instance that contains the component that defines the
	 * section.
	 */
	protected void addSectionComponent(SectionComponent sectionComponent){
		if(sectionComponent != null){
			if(this.sectionsComponents == null)
				this.sectionsComponents = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

			this.sectionsComponents.add(sectionComponent);
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildLabel()
	 */
	protected void buildLabel() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildTooltip()
	 */
	protected void buildTooltip() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildAlignment()
	 */
	protected void buildAlignment() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildEvents()
	 */
	protected void buildEvents() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		setComponentType(ComponentType.ACCORDION);

		super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		UIController uiController = getUIController();
		String actionFormName = getActionFormName();
		String name = getName();

		if(uiController == null || actionFormName == null || actionFormName.length() == 0 || name == null || name.length() == 0)
			return;

		this.currentSections = uiController.getCurrentSections(name);

		if(this.sectionsComponents != null && this.sectionsComponents.size() == 1){
			Iterator<SectionComponent> iterator = this.sectionsComponents.iterator();
			String sectionName = iterator.next().getName();

			if(this.currentSections == null)
				this.currentSections = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
			else
				this.currentSections.clear();

			this.currentSections.add(sectionName);
		}
		else if(this.sectionsComponents != null && this.sectionsComponents.size() > 1){
			if(this.currentSections != null && !this.currentSections.isEmpty()){
				for(int cont = 0 ; cont < this.currentSections.size() ; cont++){
					String currentSectionName = this.currentSections.get(cont);
					Boolean found = false;

					for(SectionComponent sectionComponent : this.sectionsComponents){
						String sectionName = sectionComponent.getName();

						if(currentSectionName.equals(sectionName)){
							found = true;

							break;
						}
					}

					if(!found){
						if(this.currentSections != null && this.currentSections.size() > 0)
							this.currentSections.remove(cont);

						cont--;
					}
				}
			}

			String lastSectionName = null;

			if(this.sectionsComponents != null && !this.sectionsComponents.isEmpty()){
				for(SectionComponent sectionComponent : this.sectionsComponents){
					String sectionName = sectionComponent.getName();

					if((sectionComponent.focus() != null && sectionComponent.focus()) || (sectionName != null && this.currentSections != null && this.currentSections.size() > 0 && this.currentSections.contains(sectionName)))
						lastSectionName = sectionName;
				}

				for(SectionComponent sectionComponent : this.sectionsComponents){
					String sectionName = sectionComponent.getName();

					if((((sectionComponent.focus() != null && sectionComponent.focus()) || (sectionName != null && sectionName.length() > 0 && this.currentSections != null && this.currentSections.size() > 0 && this.currentSections.contains(sectionName))) && this.multipleSelection != null && this.multipleSelection) || (lastSectionName != null && lastSectionName.equals(sectionName))){
						if(this.currentSections == null)
							this.currentSections = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

						this.currentSections.add(sectionName);
					}
					else{
						String currentSectionStyle = sectionComponent.getStyle();
						StringBuilder sectionStyle = new StringBuilder();

						if(currentSectionStyle != null && currentSectionStyle.length() > 0){
							sectionStyle.append(currentSectionStyle);

							if(!currentSectionStyle.endsWith(";"))
								sectionStyle.append(";");

							sectionStyle.append(" ");
						}

						sectionStyle.append("display: ");
						sectionStyle.append(VisibilityType.NONE);
						sectionStyle.append(";");

						sectionComponent.setFocus(false);
						sectionComponent.setStyle(sectionStyle.toString());

						if(this.currentSections != null && !this.currentSections.isEmpty()){
							int pos = this.currentSections.indexOf(sectionName);

							if(pos >= 0)
								this.currentSections.remove(pos);
						}
					}
				}
			}
		}

		StringBuilder nameBuffer = new StringBuilder();

		nameBuffer.append(name);
		nameBuffer.append(".");
		nameBuffer.append(UIConstants.CURRENT_SECTIONS_ATTRIBUTE_ID);

		if(this.multipleSelection != null && this.multipleSelection){
			Collection<String> sections = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

			if(this.sectionsComponents != null && !this.sectionsComponents.isEmpty()){
				String sectionName = null;

				for(SectionComponent sectionComponent : this.sectionsComponents){
					sectionName = sectionComponent.getName();

					if(sectionName != null && sectionName.length() > 0)
						sections.add(sectionName);
				}
			}

			ListPropertyComponent currentSectionsPropertyComponent = new ListPropertyComponent();

			currentSectionsPropertyComponent.setPageContext(this.pageContext);
			currentSectionsPropertyComponent.setOutputStream(getOutputStream());
			currentSectionsPropertyComponent.setActionFormName(actionFormName);
			currentSectionsPropertyComponent.setResourcesId(getResourcesId());
			currentSectionsPropertyComponent.setShowFirstOption(false);
			currentSectionsPropertyComponent.setShowLabel(false);
			currentSectionsPropertyComponent.setName(nameBuffer.toString());
			currentSectionsPropertyComponent.setMultipleSelection(this.multipleSelection);
			currentSectionsPropertyComponent.setDatasetValues(sections);
			currentSectionsPropertyComponent.setValue(this.currentSections);

			StringBuilder currentSectionsStyle = new StringBuilder();

			currentSectionsStyle.append("display: ");
			currentSectionsStyle.append(VisibilityType.NONE);
			currentSectionsStyle.append(";");

			currentSectionsPropertyComponent.setStyle(currentSectionsStyle.toString());

			try{
				currentSectionsPropertyComponent.doStartTag();
				currentSectionsPropertyComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}
		}
		else{
			HiddenPropertyComponent currentSectionPropertyComponent = new HiddenPropertyComponent();

			currentSectionPropertyComponent.setPageContext(this.pageContext);
			currentSectionPropertyComponent.setOutputStream(getOutputStream());
			currentSectionPropertyComponent.setActionFormName(actionFormName);
			currentSectionPropertyComponent.setName(nameBuffer.toString());

			if(this.currentSections != null && this.currentSections.size() > 0)
				currentSectionPropertyComponent.setValue(this.currentSections.iterator().next());

			try{
				currentSectionPropertyComponent.doStartTag();
				currentSectionPropertyComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}
		}

		String styleClass = getStyleClass();

		print("<table");

		if(styleClass != null && styleClass.length() > 0){
			print(" class=\"");
			print(styleClass);
			print("\"");
		}

		String style = getStyle();

		if(style != null && style.length() > 0){
			print(" style=\"");
			print(style);

			if(!style.endsWith(";"))
				print(";");

			print("\"");
		}

		println(">");

		println("<tr>");
		println("<td>");

		renderSections();

		println("</td>");
		println("</tr>");
		println("</table>");
	}

	/**
	 * Renders as sections of the component.
	 * 
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderSections() throws InternalErrorException{
		if(this.sectionsComponents != null && !this.sectionsComponents.isEmpty()){
			print("<table class=\"");
			print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
			println("\">");

			SectionComponent sectionComponent = null;

			for(int cont = 0 ; cont < this.sectionsComponents.size() ; cont++){
				sectionComponent = this.sectionsComponents.get(cont);

				println("<tr>");
				println("<td>");

				renderSectionHeader(sectionComponent, cont);
				renderSectionContent(sectionComponent, cont);

				println("</td>");
				println("</tr>");
			}

			println("</table>");
		}
	}

	/**
	 * Renders the header of the section.
	 * 
	 * @param sectionComponent Instance that contains the properties of the
	 * section.
	 * @param index Numeric value that contains the index of the section.
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderSectionHeader(SectionComponent sectionComponent, Integer index) throws InternalErrorException{
		String actionFormName = getActionFormName();
		String id = getId();
		String name = getName();
		String sectionName = (sectionComponent != null ? sectionComponent.getName() : null);

		if(actionFormName != null && actionFormName.length() > 0 && id != null && id.length() > 0 && name != null && name.length() > 0 && sectionName != null && sectionName.length() > 0){
			StringBuilder nameBuffer = new StringBuilder();

			nameBuffer.append(sectionName);
			nameBuffer.append(".");
			nameBuffer.append(Constants.LABEL_ATTRIBUTE_ID);

			HiddenPropertyComponent labelPropertyComponent = new HiddenPropertyComponent();

			labelPropertyComponent.setPageContext(this.pageContext);
			labelPropertyComponent.setOutputStream(getOutputStream());
			labelPropertyComponent.setActionFormName(actionFormName);
			labelPropertyComponent.setName(nameBuffer.toString());
			labelPropertyComponent.setValue(sectionComponent.getLabel());

			try{
				labelPropertyComponent.doStartTag();
				labelPropertyComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}

			print("<div id=\"");
			print(id);
			print(".");
			print(sectionName);
			print(".");
			print(UIConstants.DEFAULT_SECTION_HEADER_ID);
			print("\" class=\"");

			if(index != null && index == 0){
				print("first");
				print(StringUtil.capitalize(UIConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS));
			}
			else if(index != null && index == this.sectionsComponents.size() - 1){
				Boolean isCurrentSection = (this.currentSections != null && this.currentSections.size() > 0 ? this.currentSections.contains(sectionName) : false);

				if(!isCurrentSection){
					print("last");
					print(StringUtil.capitalize(UIConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS));
				}
				else
					print(UIConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS);
			}
			else
				print(UIConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS);

			print("\" firstSection=\"");
			print(index != null && index == 0);
			print("\" lastSection=\"");
			print(index != null && this.sectionsComponents != null && index == (this.sectionsComponents.size() - 1));
			print("\" onClick=\"setCurrentSections('");
			print(sectionName);
			print("', '");
			print(name);
			print("', ");

			if(this.multipleSelection != null)
				print(this.multipleSelection);
			else
				print(Boolean.FALSE);

			String onSelect = sectionComponent.getOnSelect();
			String onUnSelect = sectionComponent.getOnUnSelect();

			if(onSelect != null && onSelect.length() > 0){
				print(", function(){");
				print(onSelect);
				print("}");
			}

			if(onUnSelect != null && onUnSelect.length() > 0){
				print(", ");

				if(onSelect.length() == 0){
					print(Constants.DEFAULT_NULL_ID);
					print(", ");
				}

				print("function(){");
				print(onUnSelect);
				print("}");
			}

			print(");\"");
			
			String labelStyle = sectionComponent.getLabelStyle();
			
			if(labelStyle != null && labelStyle.length() > 0){
				print(" style=\"");
				print(labelStyle);
				
				if(!labelStyle.endsWith(";"))
					print(";");
				
				print("\"");
			}

			String tooltip = sectionComponent.getTooltip();

			if(tooltip != null && tooltip.length() > 0){
				print(" title=\"");
				print(tooltip);
				print("\"");
			}

			println(">");

			String label = sectionComponent.getLabel();

			if(label != null && label.length() > 0)
				println(label);

			println("</div>");
		}
	}

	/**
	 * Renders the content of the section.
	 * 
	 * @param sectionComponent Instance that contains the component that defines the
	 * section.
	 * @param index Numeric value that contains the index of the section.
	 * @throws InternalErrorException Occurs when was not possible to render.
	 */
	protected void renderSectionContent(SectionComponent sectionComponent, Integer index) throws InternalErrorException{
		if(sectionComponent == null)
			return;

		String name = getName();
		String sectionName = sectionComponent.getName();

		if(name == null || name.length() == 0 || sectionName == null || sectionName.length() == 0)
			return;

		print("<div id=\"");
		print(name);
		print(".");
		print(sectionName);
		print(".");
		print(UIConstants.DEFAULT_SECTION_CONTENT_ID);
		print("\" class=\"");

		if(index != null && index == 0){
			print("first");
			print(StringUtil.capitalize(UIConstants.DEFAULT_SECTION_CONTENT_STYLE_CLASS));
		}
		else if(index != null && this.sectionsComponents != null && index == this.sectionsComponents.size() - 1){
			print("last");
			print(StringUtil.capitalize(UIConstants.DEFAULT_SECTION_CONTENT_STYLE_CLASS));
		}
		else
			print(UIConstants.DEFAULT_SECTION_CONTENT_STYLE_CLASS);

		print("\"");

		String style = sectionComponent.getStyle();

		if(style != null && style.length() > 0){
			print(" style=\"");
			print(style);

			if(!style.endsWith(";"))
				print(";");

			print("\"");
		}

		println(">");

		String content = sectionComponent.getContent();

		if(content != null && content.length() > 0)
			println(content);

		println("</div>");
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#renderClose()
	 */
	protected void renderClose() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setMultipleSelection(null);
		setSectionsComponents(null);
		setCurrentSections(null);
	}
}