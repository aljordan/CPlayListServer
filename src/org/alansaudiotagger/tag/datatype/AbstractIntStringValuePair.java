/**
 * @author : Paul Taylor
 * <p/>
 * Version @version:$Id: AbstractIntStringValuePair.java,v 1.8 2008/07/21 10:45:40 paultaylor Exp $
 * <p/>
 * alansaudiotagger Copyright (C)2004,2005
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public  License as published by the Free Software Foundation; either version 2.1 of the License,
 * or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not,
 * you can get a copy from http://www.opensource.org/licenses/lgpl-license.php or write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * <p/>
 * Description:
 */
package org.alansaudiotagger.tag.datatype;

import java.util.Collections;
import java.util.Map;

/**
 * A two way mapping between an Integral Id and a String value
 */
public class AbstractIntStringValuePair extends AbstractValuePair<Integer, String>
{
    protected Integer key = null;

    /**
     * Get Id for Value
     */
    public Integer getIdForValue(String value)
    {
        return valueToId.get(value);
    }

    /**
     * Get value for Id
     */
    public String getValueForId(int id)
    {
        return idToValue.get(new Integer(id));
    }

    protected void createMaps()
    {
        //Create the reverse the map
        for (Map.Entry<Integer, String> entry : idToValue.entrySet())
        {
            valueToId.put(entry.getValue(), entry.getKey());
        }

        //Value List sort alphabetically
        valueList.addAll(idToValue.values());
        Collections.sort(valueList);
    }
}
