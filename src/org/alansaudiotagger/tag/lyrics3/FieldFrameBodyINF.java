/**
 *  @author : Paul Taylor
 *  @author : Eric Farng
 *
 *  Version @version:$Id: FieldFrameBodyINF.java,v 1.8 2008/07/21 10:45:49 paultaylor Exp $
 *
 *  MusicTag Copyright (C)2003,2004
 *
 *  This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser
 *  General Public  License as published by the Free Software Foundation; either version 2.1 of the License,
 *  or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 *  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License along with this library; if not,
 *  you can get a copy from http://www.opensource.org/licenses/lgpl-license.php or write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Description:
 */
package org.alansaudiotagger.tag.lyrics3;

import org.alansaudiotagger.tag.InvalidTagException;
import org.alansaudiotagger.tag.datatype.StringSizeTerminated;

import java.nio.ByteBuffer;

public class FieldFrameBodyINF extends AbstractLyrics3v2FieldFrameBody
{
    /**
     * Creates a new FieldBodyINF datatype.
     */
    public FieldFrameBodyINF()
    {
        //        this.setObject("Additional Information", "");
    }

    public FieldFrameBodyINF(FieldFrameBodyINF body)
    {
        super(body);
    }

    /**
     * Creates a new FieldBodyINF datatype.
     *
     * @param additionalInformation
     */
    public FieldFrameBodyINF(String additionalInformation)
    {
        this.setObjectValue("Additional Information", additionalInformation);
    }

    /**
     * Creates a new FieldBodyINF datatype.
     */
    public FieldFrameBodyINF(ByteBuffer byteBuffer) throws InvalidTagException
    {
        this.read(byteBuffer);

    }

    /**
     * @param additionalInformation
     */
    public void setAdditionalInformation(String additionalInformation)
    {
        setObjectValue("Additional Information", additionalInformation);
    }

    /**
     * @return
     */
    public String getAdditionalInformation()
    {
        return (String) getObjectValue("Additional Information");
    }

    /**
     * @return
     */
    public String getIdentifier()
    {
        return "INF";
    }

    /**
     *
     */
    protected void setupObjectList()
    {
        objectList.add(new StringSizeTerminated("Additional Information", this));
    }
}
