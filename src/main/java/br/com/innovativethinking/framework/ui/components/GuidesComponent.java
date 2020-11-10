package br.com.innovativethinking.framework.ui.components;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.exceptions.InternalErrorException;
import br.com.innovativethinking.framework.ui.components.types.VisibilityType;
import br.com.innovativethinking.framework.ui.constants.UIConstants;
import br.com.innovativethinking.framework.ui.controller.UIController;
import br.com.innovativethinking.framework.util.PropertyUtil;
import br.com.innovativethinking.framework.util.types.AlignmentType;
import br.com.innovativethinking.framework.util.types.ComponentType;

/**
 * Class that defines the guides component.
 * 
 * @author fvilarinho
 * @since 1.0.0
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
public class GuidesComponent extends BaseActionFormComponent{
	private static final long serialVersionUID = -4321693551104130259L;

	private List<GuideComponent> guidesComponents = null;
	private Boolean              showNavigation   = null;

	/**
	 * Returns the instance that contains the list of guides.
	 *
	 * @return Instance that contains the list of guides.
	 */
	protected List<GuideComponent> getGuidesComponents(){
		return this.guidesComponents;
	}

	/**
	 * Defines the instance that contains the list of guides.
	 *
	 * @param guidesComponents Instance that contains the list of guides.
	 */
	protected void setGuidesComponents(List<GuideComponent> guidesComponents){
		this.guidesComponents = guidesComponents;
	}

	/**
	 * Indicates if the guide navigation should be shown.
	 * 
	 * @return True/False.
	 */
	public Boolean showNavigation(){
		return this.showNavigation;
	}

	/**
	 * Defines if the guide navigation should be shown.
	 * 
	 * @param showNavigation True/False.
	 */
	public void setShowNavigation(Boolean showNavigation){
		this.showNavigation = showNavigation;
	}

	/**
	 * Adds a new guide component.
	 * 
	 * @param guideContent Instance that contains the guide component.
	 */
	protected void addGuideComponent(GuideComponent guideContent){
		if(guideContent != null){
			if(this.guidesComponents == null)
				this.guidesComponents = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

			this.guidesComponents.add(guideContent);
		}
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildLabel()
	 */
	protected void buildLabel() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildTooltip()
	 */
	protected void buildTooltip() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildAlignment()
	 */
	protected void buildAlignment() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildEvents()
	 */
	protected void buildEvents() throws InternalErrorException{
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildRestrictions()
	 */
	protected void buildRestrictions() throws InternalErrorException{
		super.buildRestrictions();

		if(this.showNavigation == null)
			this.showNavigation = true;
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#initialize()
	 */
	protected void initialize() throws InternalErrorException{
		setComponentType(ComponentType.GUIDES);

		super.initialize();
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderOpen()
	 */
	protected void renderOpen() throws InternalErrorException{
		print("<table class=\"");
		print(UIConstants.DEFAULT_GUIDES_STYLE_CLASS);
		print("\"");

		String width = getWidth();

		if(width != null && width.length() > 0){
			print(" style=\"width: ");
			print(width);

			if(!width.endsWith(";"))
				print(";");

			print("\"");
		}

		println(">");
		println("<tr>");
		println("<td>");
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderBody()
	 */
	protected void renderBody() throws InternalErrorException{
		UIController uiController = getUIController();
		String actionFormName = getActionFormName();
		String name = getName();
		Boolean enabled = isEnabled();

		if(uiController != null && actionFormName != null && actionFormName.length() > 0 && name != null && name.length() > 0 && enabled != null && enabled && this.guidesComponents != null && !this.guidesComponents.isEmpty()){
			String currentGuide = uiController.getCurrentGuide(name);

			if(this.guidesComponents.size() == 1){
				Iterator<GuideComponent> iterator = this.guidesComponents.iterator();

				if(iterator.hasNext())
					currentGuide = iterator.next().getName();
			}
			else if(this.guidesComponents.size() > 1){
				if(currentGuide == null || currentGuide.length() == 0){
					Iterator<GuideComponent> iterator = this.guidesComponents.iterator();

					if(iterator.hasNext())
						currentGuide = iterator.next().getName();
				}
				else{
					Boolean found = false;

					for(GuideComponent guideComponent : this.guidesComponents){
						Boolean guideFocus = guideComponent.focus();
						String guideName = guideComponent.getName();

						if((guideFocus != null && guideFocus) || (currentGuide != null && guideName != null && currentGuide.equals(guideName))){
							found = true;
							currentGuide = guideName;
						}
					}

					if(!found){
						Iterator<GuideComponent> iterator = this.guidesComponents.iterator();

						if(iterator.hasNext())
							currentGuide = iterator.next().getName();
					}
				}
			}

			Integer guidesSize = this.guidesComponents.size();
			Double guideWidthAmount = 100d;
			String guideName = null;
			String guideTooltip = null;
			String guideWidth = null;
			String guideOnSelect = null;

			for(GuideComponent guideComponent : this.guidesComponents){
				guideWidth = guideComponent.getWidth();

				if(guideWidth != null && guideWidth.length() > 0){
					guideWidthAmount -= Double.parseDouble(guideWidth);

					guidesSize--;
				}
			}

			guideWidthAmount = (guideWidthAmount / guidesSize);

			StringBuilder nameBuffer = new StringBuilder();

			nameBuffer.append(name);
			nameBuffer.append(".");
			nameBuffer.append(UIConstants.CURRENT_GUIDE_ATTRIBUTE_ID);

			HiddenPropertyComponent currentGuidePropertyComponent = new HiddenPropertyComponent();

			currentGuidePropertyComponent.setPageContext(this.pageContext);
			currentGuidePropertyComponent.setOutputStream(getOutputStream());
			currentGuidePropertyComponent.setActionFormName(actionFormName);
			currentGuidePropertyComponent.setName(nameBuffer.toString());
			currentGuidePropertyComponent.setValue(currentGuide);

			try{
				currentGuidePropertyComponent.doStartTag();
				currentGuidePropertyComponent.doEndTag();
			}
			catch(JspException e){
				throw new InternalErrorException(e);
			}

			print("<table class=\"");
			print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
			println("\">");
			println("<tr>");

			for(GuideComponent guideComponent : this.guidesComponents){
				guideName = guideComponent.getName();
				guideWidth = guideComponent.getWidth();

				print("<td valign=\"");
				print(AlignmentType.BOTTOM);
				print("\" width=\"");

				if(guideWidth != null && guideWidth.length() > 0)
					print(guideWidth);
				else{
					print(guideWidthAmount);
					print("%");
				}

				print("\">");

				nameBuffer.delete(0, nameBuffer.length());
				nameBuffer.append(guideComponent.getName());
				nameBuffer.append(".");
				nameBuffer.append(Constants.LABEL_ATTRIBUTE_ID);

				HiddenPropertyComponent labelPropertyComponent = new HiddenPropertyComponent();

				labelPropertyComponent.setPageContext(this.pageContext);
				labelPropertyComponent.setOutputStream(getOutputStream());
				labelPropertyComponent.setActionFormName(actionFormName);
				labelPropertyComponent.setName(nameBuffer.toString());
				labelPropertyComponent.setValue(guideComponent.getLabel());

				try{
					labelPropertyComponent.doStartTag();
					labelPropertyComponent.doEndTag();
				}
				catch(JspException e){
					throw new InternalErrorException(e);
				}

				print("<table class=\"");
				print(UIConstants.DEFAULT_CONTENT_PANEL_STYLE_CLASS);
				println("\">");
				println("<tr>");
				print("<td id=\"");
				print(name);
				print(".");
				print(guideName);
				print(".");
				print(UIConstants.DEFAULT_GUIDE_ID);
				print("\" class=\"");

				if(guideName != null && currentGuide != null && guideName.equals(currentGuide))
					print(UIConstants.DEFAULT_CURRENT_GUIDE_STYLE_CLASS);
				else
					print(UIConstants.DEFAULT_GUIDE_STYLE_CLASS);

				print("\"");

				if(enabled){
					print(" onClick=\"setCurrentGuide('");
					print(guideName);
					print("', '");
					print(name);
					print("'");

					guideOnSelect = guideComponent.getOnSelect();

					if(guideOnSelect != null && guideOnSelect.length() > 0){
						print(", ");
						print(guideOnSelect);
					}

					print(");\"");
				}

				guideTooltip = guideComponent.getTooltip();

				if(guideTooltip != null && guideTooltip.length() > 0){
					print(" title=\"");
					print(guideTooltip);
					print("\"");
				}

				println(">");
				println(guideComponent.getLabel());
				println("</td>");
				println("</tr>");
				println("</table>");

				println("</td>");
			}

			println("</tr>");
			println("</table>");

			println("</td>");
			println("</tr>");

			println("<tr>");
			println("<td>");

			String guidesHeight = getHeight();
			String guideContent = null;
			GuideComponent previousGuideComponent = null;
			GuideComponent nextGuideComponent = null;
			PreviousGuideButtonComponent previousGuideButtonComponent = null;
			NextGuideButtonComponent nextGuideButtonComponent = null;
			int cont = 0;

			for(GuideComponent guideComponent : this.guidesComponents){
				guideName = guideComponent.getName();
				guideContent = guideComponent.getContent();

				print("<div id=\"");
				print(name);
				print(".");
				print(guideName);
				print(".");
				print(UIConstants.DEFAULT_GUIDE_CONTENT_ID);
				print("\"");

				if(currentGuide == null || guideName == null || !guideName.equals(currentGuide)){
					print(" style=\"display: ");
					print(VisibilityType.NONE);
					print(";\"");
				}

				println(">");

				print("<table class=\"");
				print(UIConstants.DEFAULT_GUIDE_CONTENT_STYLE_CLASS);
				print("\"");

				if(guidesHeight != null && guidesHeight.length() > 0){
					print(" style=\"height: ");
					print(guidesHeight);

					if(!guidesHeight.endsWith(";"))
						print(";");

					print("\"");
				}

				println(">");
				println("<tr>");
				println("<td>");

				if(guideContent != null && guideContent.length() > 0)
					println(guideContent);

				println("</td>");
				println("</tr>");

				if(this.showNavigation != null && this.showNavigation){
					if(guidesSize > 1){
						println("<tr>");
						print("<td class=\"");
						print(UIConstants.DEFAULT_GUIDES_BUTTONS_STYLE_CLASS);
						println("\">");
						println("<hr size=\"1\"/>");

						if(cont > 0 && enabled != null && enabled){
							previousGuideComponent = this.guidesComponents.get(cont - 1);

							if(previousGuideComponent.isEnabled() != null && previousGuideComponent.isEnabled()){
								previousGuideButtonComponent = new PreviousGuideButtonComponent(previousGuideComponent, this);

								try{
									previousGuideButtonComponent.doStartTag();
									previousGuideButtonComponent.doEndTag();
								}
								catch(JspException e){
									throw new InternalErrorException(e);
								}
							}
						}

						if(cont >= 0 && (cont != guidesSize - 1) && enabled != null && enabled){
							nextGuideComponent = this.guidesComponents.get(cont + 1);

							if(nextGuideComponent.isEnabled() != null && nextGuideComponent.isEnabled()){
								nextGuideButtonComponent = new NextGuideButtonComponent(nextGuideComponent, this);

								try{
									nextGuideButtonComponent.doStartTag();
									nextGuideButtonComponent.doEndTag();
								}
								catch(JspException e){
									throw new InternalErrorException(e);
								}
							}
						}

						println("</td>");
						println("</tr>");
					}
				}

				println("</table>");

				println("</div>");

				cont++;
			}
		}
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseComponent#renderClose()
	 */
	protected void renderClose() throws InternalErrorException{
		println("</td>");
		println("</tr>");
		println("</table>");
	}

	/**
	 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setShowNavigation(null);
		setGuidesComponents(null);
	}

	/**
	 * Class that defines a guide button component.
	 * 
	 * @author fvilarinho
	 * @since 1.0.0
	 */
	private abstract class GuideButtonComponent extends ButtonComponent{
		private static final long serialVersionUID = -9210783180699277267L;

		private GuideComponent  guideComponent  = null;
		private GuidesComponent guidesComponent = null;

		/**
		 * Constructor - Initializes the component.
		 * 
		 * @param guideComponent Instance that contains the guide component.
		 * @param guidesComponent Instance that contains the guides component.
		 */
		public GuideButtonComponent(GuideComponent guideComponent, GuidesComponent guidesComponent){
			super();

			if(guideComponent != null && guidesComponent != null){
				setPageContext(guidesComponent.getPageContext());
				setOutputStream(guidesComponent.getOutputStream());
				setActionFormName(guidesComponent.getActionFormName());
				setGuideComponent(guideComponent);
				setGuidesComponent(guidesComponent);
			}
		}

		/**
		 * Defines the guide component.
		 * 
		 * @param guideComponent instance of the guide component.
		 */
		protected void setGuideComponent(GuideComponent guideComponent){
			this.guideComponent = guideComponent;
		}

		/**
		 * Defines the guides component.
		 * 
		 * @param guidesComponent instance of the guides component.
		 */
		protected void setGuidesComponent(GuidesComponent guidesComponent){
			this.guidesComponent = guidesComponent;
		}

		/**
		 * @see br.com.innovativethinking.framework.ui.components.ButtonComponent#buildEvents()
		 */
		protected void buildEvents() throws InternalErrorException{
			if(this.guideComponent == null || this.guidesComponent == null)
				return;

			String guideName = this.guideComponent.getName();
			String guidesName = this.guidesComponent.getName();
			StringBuilder onClick = new StringBuilder();

			onClick.append("setCurrentGuide('");
			onClick.append(guideName);
			onClick.append("', '");
			onClick.append(guidesName);
			onClick.append("'");

			String guideOnSelect = this.guideComponent.getOnSelect();

			if(guideOnSelect != null && guideOnSelect.length() > 0){
				onClick.append(", ");
				onClick.append(guideOnSelect);
			}

			onClick.append(");");

			setOnClick(onClick.toString());

			super.buildEvents();
		}

		/**
		 * @see br.com.innovativethinking.framework.ui.components.BaseActionFormComponent#buildResources()
		 */
		protected void buildResources() throws InternalErrorException{
			setResourcesId(UIConstants.DEFAULT_GUIDES_RESOURCES_ID);

			super.buildResources();
		}
	}

	/**
	 * Class that defines the previous guide button component.
	 * 
	 * @author fvilarinho
	 * @since 1.0.0
	 */
	private class PreviousGuideButtonComponent extends GuideButtonComponent{
		private static final long serialVersionUID = -2478586421393262793L;

		/**
		 * Constructor - Initializes the component.
		 * 
		 * @param guideComponent Instance that contains the guide component.
		 * @param guidesComponent Instance that contains the guides component.
		 */
		public PreviousGuideButtonComponent(GuideComponent guideComponent, GuidesComponent guidesComponent){
			super(guideComponent, guidesComponent);
		}

		/**
		 * @see br.com.innovativethinking.framework.ui.components.GuidesComponent.GuideButtonComponent#buildResources()
		 */
		protected void buildResources() throws InternalErrorException{
			setResourcesId(UIConstants.DEFAULT_GUIDES_RESOURCES_ID);
			setResourcesKey(UIConstants.DEFAULT_GUIDE_PREVIOUS_BUTTON_ID);

			super.buildResources();
		}

		/**
		 * @see br.com.innovativethinking.framework.ui.components.ButtonComponent#buildStyleClass()
		 */
		protected void buildStyleClass() throws InternalErrorException{
			setStyleClass(UIConstants.DEFAULT_GUIDE_PREVIOUS_BUTTON_STYLE_CLASS);

			super.buildStyleClass();
		}
	}

	/**
	 * Class that defines the next guide button component.
	 * 
	 * @author fvilarinho
	 * @since 1.0.0
	 */
	private class NextGuideButtonComponent extends GuideButtonComponent{
		private static final long serialVersionUID = -2197037654486956330L;

		/**
		 * Constructor - Initializes the component.
		 * 
		 * @param guideComponent Instance that contains the guide component.
		 * @param guidesComponent Instance that contains the guides component.
		 */
		public NextGuideButtonComponent(GuideComponent guideComponent, GuidesComponent guidesComponent){
			super(guideComponent, guidesComponent);
		}

		/**
		 * @see br.com.innovativethinking.framework.ui.components.GuidesComponent.GuideButtonComponent#buildResources()
		 */
		protected void buildResources() throws InternalErrorException{
			setResourcesId(UIConstants.DEFAULT_GUIDES_RESOURCES_ID);
			setResourcesKey(UIConstants.DEFAULT_GUIDE_NEXT_BUTTON_ID);

			super.buildResources();
		}

		/**
		 * @see br.com.innovativethinking.framework.ui.components.ButtonComponent#buildStyleClass()
		 */
		protected void buildStyleClass() throws InternalErrorException{
			setStyleClass(UIConstants.DEFAULT_GUIDE_NEXT_BUTTON_STYLE_CLASS);

			super.buildStyleClass();
		}
	}
}