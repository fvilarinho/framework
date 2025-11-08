package br.com.concepting.framework.processors.interfaces;

import br.com.concepting.framework.exceptions.InternalErrorException;

/**
 * Interface that defines the basic implementation of annotation processors.
 *
 * @author fvilarinho
 * @since 3.0.0
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
public interface IAnnotationProcessor{
    /**
     * Initializes the processing.
     *
     * @throws InternalErrorException Occurs when was not possible to execute
     * the operation.
     */
    void process() throws InternalErrorException;
}
