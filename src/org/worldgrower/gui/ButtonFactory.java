/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonFactory {

	public static JButton createButton(String text, ImageIcon icon) {
		JButton button = new JGradientButton(text, icon);
		button.setBorder(new RoundedBorder(5));
		return button;
	}
	
	public static JButton createButton(String text) {
		JButton button = new JGradientButton(text);
		button.setBorder(new RoundedBorder(5));
		return button;
	}
}
