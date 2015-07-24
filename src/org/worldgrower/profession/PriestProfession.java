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
package org.worldgrower.profession;

import java.util.Arrays;
import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.attribute.SkillProperty;
import org.worldgrower.goal.Goal;
import org.worldgrower.goal.Goals;

public class PriestProfession implements Profession {

	@Override
	public String getDescription() {
		return "priest";
	}

	@Override
	public List<Goal> getProfessionGoals() {
		return Arrays.asList(
				Goals.CHOOSE_DEITY_GOAL,
				Goals.SHRINE_TO_DEITY_GOAL,
				Goals.LIBRARY_GOAL
				);
	}

	@Override
	public SkillProperty getSkillProperty() {
		return Constants.RELIGION_SKILL;
	}
}