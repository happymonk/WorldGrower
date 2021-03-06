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
package org.worldgrower.conversation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.OperationInfo;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.ReasonsImpl;
import org.worldgrower.history.Turn;
import org.worldgrower.profession.Professions;

public class UTestProfessionReasonConversation {

	private final ProfessionReasonConversation conversation = Conversations.PROFESSION_REASON_CONVERSATION;
	
	@Test
	public void testGetReplyPhrases() {
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.PROFESSION, Professions.FARMER_PROFESSION);
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.PROFESSION, Professions.FARMER_PROFESSION);
		target.setProperty(Constants.REASONS, new ReasonsImpl());
		target.getProperty(Constants.REASONS).addReason(Constants.PROFESSION, "I like harvesting food");
		
		ConversationContext context = new ConversationContext(performer, target, null, null, null, 0);
		List<Response> replyPhrases = conversation.getReplyPhrases(context);
		assertEquals(4, replyPhrases.size());
		assertEquals("I like harvesting food", replyPhrases.get(0).getResponsePhrase());
		assertEquals("I don't have a profession", replyPhrases.get(1).getResponsePhrase());
		assertEquals("It's still the same as the last time you asked, I like harvesting food", replyPhrases.get(2).getResponsePhrase());
		assertEquals("Like I said before, I like harvesting food", replyPhrases.get(3).getResponsePhrase());
	}
	
	@Test
	public void testGetReplyPhrase() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.PROFESSION, Professions.FARMER_PROFESSION);
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.PROFESSION, Professions.FARMER_PROFESSION);
		target.setProperty(Constants.REASONS, new ReasonsImpl());
		target.getProperty(Constants.REASONS).addReason(Constants.PROFESSION, "I like harvesting food");
		
		ConversationContext context = new ConversationContext(performer, target, null, null, world, 0);
		assertEquals(0, conversation.getReplyPhrase(context).getId());
	
		target.setProperty(Constants.REASONS, new ReasonsImpl());
		assertEquals(1, conversation.getReplyPhrase(context).getId());
		
		world.getHistory().setNextAdditionalValue(target.getProperty(Constants.PROFESSION));
		world.getHistory().actionPerformed(new OperationInfo(performer, target, Conversations.createArgs(Conversations.PROFESSION_REASON_CONVERSATION), Actions.TALK_ACTION), new Turn());
		assertEquals(2, conversation.getReplyPhrase(context).getId());
	}
	
	@Test
	public void testGetReplyPhraseNewReason() {
		World world = new WorldImpl(1, 1, null, null);
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.PROFESSION, Professions.FARMER_PROFESSION);
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.PROFESSION, Professions.FARMER_PROFESSION);
		target.setProperty(Constants.REASONS, new ReasonsImpl());
		target.getProperty(Constants.REASONS).addReason(Constants.PROFESSION, "I like harvesting food");
		
		ConversationContext context = new ConversationContext(performer, target, null, null, world, 0);
		world.getHistory().actionPerformed(new OperationInfo(performer, target, Conversations.createArgs(Conversations.PROFESSION_REASON_CONVERSATION), Actions.TALK_ACTION), new Turn());
		target.setProperty(Constants.PROFESSION, Professions.TAX_COLLECTOR_PROFESSION);
		target.getProperty(Constants.REASONS).addReason(Constants.PROFESSION, "I like collecting taxes");
		assertEquals(3, conversation.getReplyPhrase(context).getId());
	}
	
	@Test
	public void testGetQuestionPhrases() {
		WorldObject performer = TestUtils.createIntelligentWorldObject(1, Constants.NAME, "performer");
		WorldObject target = TestUtils.createIntelligentWorldObject(2, Constants.NAME, "target");
		
		List<Question> questions = conversation.getQuestionPhrases(performer, target, null, null, null);
		assertEquals(1, questions.size());
		assertEquals("Why did you choose your profession?", questions.get(0).getQuestionPhrase());
	}
}
