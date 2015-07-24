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
package org.worldgrower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.worldgrower.goal.Goal;

public class GoalChangedCalculator {

	private final GoalObstructedHandler goalObstructedHandler;
	private Map<Integer, List<GoalEvaluation>> targetGoalEvaluations;
	
	public GoalChangedCalculator(GoalObstructedHandler goalObstructedHandler) {
		this.goalObstructedHandler = goalObstructedHandler;
	}

	public void recordStartState(WorldObject performer, WorldObject target, World world) {
		List<WorldObject> actors = getActors(performer, world);
		targetGoalEvaluations = new HashMap<>();
		for(WorldObject actor : actors) {
			List<Goal> targetGoals = actor.getPriorities(world);
			List<GoalEvaluation> actorGoalEvaluations = new ArrayList<>();
			for(Goal targetGoal : targetGoals) {
				actorGoalEvaluations.add(new GoalEvaluation(targetGoal, targetGoal.evaluate(actor, world)));
			}
			targetGoalEvaluations.put(actor.getProperty(Constants.ID), actorGoalEvaluations);
		}
	}

	private List<WorldObject> getActors(WorldObject performer, World world) {
		List<WorldObject> actors = world.findWorldObjects(w -> w.isControlledByAI() && w.hasIntelligence() && !w.equals(performer));
		return actors;
	}
	
	public void recordEndState(WorldObject performer, WorldObject target, ManagedOperation managedOperation, int[] args, World world) {
		List<WorldObject> actors = getActors(performer, world);
		for(WorldObject actor : actors) {
			List<Goal> targetGoals = actor.getPriorities(world);
			List<GoalEvaluation> targetGoalEval = targetGoalEvaluations.get(actor.getProperty(Constants.ID));
			
			for(int i=0; i<targetGoalEval.size(); i++) {
				Goal targetGoal = targetGoals.get(i);
				GoalEvaluation oldGoalEval = GoalEvaluation.find(targetGoal, targetGoalEval);
				if (oldGoalEval != null) {
					int oldGoalEvaluation = oldGoalEval.getEvaluation();
					int newGoalEvaluation = targetGoal.evaluate(actor, world);
					
					if (newGoalEvaluation < oldGoalEvaluation) {
						goalObstructedHandler.goalHindered(performer, actor, targetGoals.size() - i, oldGoalEvaluation - newGoalEvaluation, target, managedOperation, args, world);
					}
				}
			}
		}
	}
	
	private static class GoalEvaluation {
		private final Goal goal;
		private final int evaluation;
		
		public GoalEvaluation(Goal goal, int evaluation) {
			super();
			this.goal = goal;
			this.evaluation = evaluation;
		}

		public Goal getGoal() {
			return goal;
		}

		public int getEvaluation() {
			return evaluation;
		}
		
		public static GoalEvaluation find(Goal goal, List<GoalEvaluation> goalEvaluations) {
			for(GoalEvaluation goalEvaluation : goalEvaluations) {
				if (goalEvaluation.getGoal() == goal) {
					return goalEvaluation;
				}
			}
			return null;
		}
	}
}