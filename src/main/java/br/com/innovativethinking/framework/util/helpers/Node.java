package br.com.innovativethinking.framework.util.helpers;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.com.innovativethinking.framework.constants.Constants;
import br.com.innovativethinking.framework.model.annotations.Property;
import br.com.innovativethinking.framework.util.PropertyUtil;

/**
 * Class that defines the basic implementation of a node to be used in a tree
 * data structure.
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
public abstract class Node implements Serializable, Cloneable{
	private static final long serialVersionUID = -9107989395547430445L;

	@Property
	@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="class")
	private Node parent = null;

	@Property
	@JsonIgnore
	private List<Node> children = null;

	@Property(isUnique=true)
	private Integer index = null;

	@Property(isUnique=true)
	private Integer level = null;

	/**
	 * Returns the level of the node.
	 * 
	 * @return Numeric value that contains the level.
	 */
	public Integer getLevel(){
		return this.level;
	}

	/**
	 * Defines the level of the node.
	 * 
	 * @param level Numeric value that contains the level.
	 */
	public void setLevel(Integer level){
		this.level = level;
	}

	/**
	 * Returns the index of the node in a list.
	 *
	 * @return Numeric value that contains the index.
	 */
	public Integer getIndex(){
		return this.index;
	}

	/**
	 * Defines the index of the node in a list.
	 *
	 * @param index Numeric value that contains the index.
	 */
	public void setIndex(Integer index){
		this.index = index;
	}

	/**
	 * Returns the instance of the parent.
	 * 
	 * @param <N> Class that defines the parent.
	 * @return Instance that contains the parent.
	 */
	@SuppressWarnings("unchecked")
	public <N extends Node> N getParent(){
		return (N)this.parent;
	}

	/**
	 * Defines the instance of the parent.
	 * 
	 * @param parent Instance that contains the parent.
	 * @param sync Synchronize all children.
	 */
	protected void setParent(Node parent, Boolean sync){
		this.parent = parent;

		if(parent == null)
			return;

		if(sync)
			parent.addChild(this);
	}

	/**
	 * Defines the instance of the parent.
	 * 
	 * @param parent Instance that contains the parent.
	 */
	public void setParent(Node parent){
		setParent(parent, true);
	}

	/**
	 * Returns the children.
	 *
	 * @param <C> Class that defines the list of children.
	 * @return List of children.
	 */
	@SuppressWarnings("unchecked")
	public <C extends List<? extends Node>> C getChildren(){
		return (C)this.children;
	}

	/**
	 * Adds a child.
	 * 
	 * @param child Instance that contains the child.
	 */
	public void addChild(Node child){
		if(child == null)
			return;

		if(this.children == null)
			this.children = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);

		if(!this.children.contains(child)){
			child.setParent(this, false);

			this.children.add(child);
		}
	}

	/**
	 * Removes a child.
	 * 
	 * @param child Instance that contains the child.
	 */
	public void removeChild(Node child){
		if(child == null)
			return;
		
		if(this.children != null && this.children.size() > 0)
			this.children.remove(child);
	}

	/**
	 * Defines the list of children.
	 * 
	 * @param children List of children.
	 */
	@SuppressWarnings("unchecked")
	public void setChildren(List<? extends Node> children){
		this.children = (List<Node>)children;
	}

	/**
	 * Indicates if the node has children.
	 * 
	 * @return True/False.
	 */
	public Boolean hasChildren(){
		if(this.children != null && !this.children.isEmpty()){
			Node childNodeParent = null;

			for(Node childNode : this.children){
				try{
					childNodeParent = childNode.getParent();

					if(childNodeParent != null && childNodeParent.equals(this))
						return true;
				}
				catch(Throwable e){
				}
			}
		}

		return false;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}