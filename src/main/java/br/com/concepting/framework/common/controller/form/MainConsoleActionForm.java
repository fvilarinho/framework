package br.com.concepting.framework.common.controller.form;

import br.com.concepting.framework.common.model.MainConsoleModel;
import br.com.concepting.framework.controller.form.BaseActionForm;

import java.io.Serial;

/**
 * Class that defines the basic implementation of the main console form.
 *
 * @param <M> Class that defines the main console data model.
 * @author fvilarinho
 * @version 3.2.0
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
public class MainConsoleActionForm<M extends MainConsoleModel> extends BaseActionForm<M> {
    @Serial
    private static final long serialVersionUID = 2246476401602563788L;
}