package br.com.concepting.framework.resources.helpers;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Class responsible to store the action form properties.
 * 
 * @author fvilarinho
 * @since 3.3.0
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
public class ActionFormResources implements Serializable{
	private static final long serialVersionUID = 8287693656398359248L;

	private String                                 name     = null;
	private String                                 clazz    = null;
	private String                                 action   = null;
	private Collection<ActionFormForwardResources> forwards = null;

	/**
	 * Returns the action identifier of the action form.
	 * 
	 * @return String that contains the action identifier.
	 */
	public String getAction(){
		return this.action;
	}

	/**
	 * Defines the action identifier of the action form.
	 * 
	 * @param action String that contains the action identifier.
	 */
	public void setAction(String action){
		this.action = action;
	}

	/**
	 * Returns the action form class.
	 * 
	 * @return String that contains the action form class.
	 */
	public String getClazz(){
		return this.clazz;
	}

	/**
	 * Defines the action form class.
	 * 
	 * @param clazz String that contains the action form class.
	 */
	public void setClazz(String clazz){
		this.clazz = clazz;
	}

	/**
	 * Returns the identifier of the action form.
	 * 
	 * @return String that contains the identifier.
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Defines the identifier of the action form.
	 * 
	 * @param name String that contains the identifier.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Returns the list of forwards of the action form.
	 *
	 * @param <C> Class that defines the list of forwards. 
	 * @return Instance that contains the forwards of the action form.
	 */
	@SuppressWarnings("unchecked")
	public <C extends Collection<ActionFormForwardResources>> C getForwards(){
		return (C)this.forwards;
	}

	/**
	 * Defines the list of forwards of the action form.
	 * 
	 * @param forwards Instance that contains the forwards of the action form.
	 */
	public void setForwards(Collection<ActionFormForwardResources> forwards){
		this.forwards = forwards;
	}

	/**
	 * Returns a specific forward of the action form.
	 * 
	 * @param forwardName String that contains the identifier of the forward.
	 * @return String that contains the forward of the action form.
	 */
	public ActionFormForwardResources getForward(String forwardName){
		if(this.forwards != null && !this.forwards.isEmpty()){
			Iterator<ActionFormForwardResources> iterator = this.forwards.iterator();
			ActionFormForwardResources item = null;
			
			while(iterator.hasNext()){
				item = iterator.next();

				if(item.getName().equals(forwardName))
					return item;
			}
		}

		return null;
	}
}