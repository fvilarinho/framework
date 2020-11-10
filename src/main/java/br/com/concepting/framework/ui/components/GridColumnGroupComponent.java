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
public class GridColumnGroupComponent extends GridColumnComponent{
	private static final long serialVersionUID = 6723734982419563034L;

	private Boolean aggregate = null;

	/**
	 * Indicates if the column is for aggregation.
	 * 
	 * @return True/False.
	 */
	public Boolean aggregate(){
		return this.aggregate;
	}

	/**
	 * Defines if the column is for aggregation.
	 * 
	 * @param aggregate True/False.
	 */
	public void setAggregate(Boolean aggregate){
		this.aggregate = aggregate;
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BaseActionFormComponent#buildName()
	 */
	protected void buildName() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.ui.components.BasePropertyComponent#buildAlignment()
	 */
	protected void buildAlignment() throws InternalErrorException{
		AlignmentType alignment = getAlignmentType();

		if(alignment == null){
			if(this.aggregate == null || !this.aggregate)
				alignment = AlignmentType.CENTER;
			else
				alignment = AlignmentType.LEFT;

			setAlignmentType(alignment);
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.components.GridColumnComponent#clearAttributes()
	 */
	protected void clearAttributes() throws InternalErrorException{
		super.clearAttributes();

		setAggregate(null);
	}
}