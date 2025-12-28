package br.com.concepting.framework.controller;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.form.ActionFormController;
import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.controller.form.constants.ActionFormConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.security.controller.SecurityController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

/**
 * Class responsible to route the request to the right UI.
 *
 * @author fvilarinho
 * @version 3.3.0
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
@WebServlet(name = "actions", urlPatterns = ActionFormConstants.DEFAULT_ACTIONS_URL_PATTERN)
public class ActionFormServlet extends HttpServlet{
	@Serial
    private static final long serialVersionUID = -6893835539662603177L;

	private SystemController systemController = null;
	private ActionFormController actionFormController = null;
	private SecurityController securityController = null;

	/**
	 * Initializes the action processing.
	 *
	 * @param request Instance that contains the request attributes.
	 * @param response Instance that contains the response attributes.
	 */
	protected void init(HttpServletRequest request, HttpServletResponse response) {
		this.systemController = new SystemController(request, response);
		this.actionFormController = this.systemController.getActionFormController();
		this.securityController = this.systemController.getSecurityController();
	}

	/**
	 * Execute the action processing.
	 *
	 * @param request Instance that contains the request attributes.
	 * @param response Instance that contains the response attributes.
	 */
	protected void execute(HttpServletRequest request, HttpServletResponse response) {
		this.init(request, response);

		response.setCharacterEncoding(Constants.DEFAULT_UNICODE_ENCODING);

		String uri = this.systemController.getURI();
		BaseActionForm<? extends BaseModel> actionFormInstance;

		try {
			actionFormInstance = this.actionFormController.getActionFormInstance();

			if (actionFormInstance == null)
				throw new InvalidResourcesException(uri);

			actionFormInstance.processRequest(this.systemController, this.actionFormController, this.securityController);
		}
		catch(InternalErrorException e) {
			this.systemController.forward(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		execute(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		execute(request, response);
	}
}