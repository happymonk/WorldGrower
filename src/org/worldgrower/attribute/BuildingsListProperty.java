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
package org.worldgrower.attribute;

import java.io.ObjectStreamException;
import java.util.List;

import org.worldgrower.WorldObject;

public class BuildingsListProperty implements ManagedProperty<BuildingList>, IdContainer {
	
	private final String name;
	private final int ordinal = OrdinalGenerator.getNextOrdinal();
	
	public BuildingsListProperty(String name, List<ManagedProperty<?>> allProperties) {
		this.name = name;
		allProperties.add(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void checkValue(BuildingList value) {
		if (value == null) {
			throw new IllegalStateException("value " + value + " is null");
		}
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int getOrdinal() {
		return ordinal;
	}
	
	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}

	@Override
	public BuildingList copy(Object value) {
		return ((BuildingList)value).copy();
	}

	@Override
	public void remove(WorldObject worldObject, ManagedProperty<?> property, int id) {
		BuildingsListProperty idMapProperty = (BuildingsListProperty) property;
		worldObject.getProperty(idMapProperty).remove(id);
	}
}
