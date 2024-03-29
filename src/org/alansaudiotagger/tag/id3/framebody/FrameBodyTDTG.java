/**
 *  @author : Paul Taylor
 *  @author : Eric Farng
 *
 *  Version @version:$Id: FrameBodyTDTG.java,v 1.8 2008/07/21 10:45:44 paultaylor Exp $
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
 *
 */
package org.alansaudiotagger.tag.id3.framebody;

import org.alansaudiotagger.tag.InvalidTagException;
import org.alansaudiotagger.tag.id3.ID3v24Frames;

import java.nio.ByteBuffer;


public class FrameBodyTDTG extends AbstractFrameBodyTextInfo implements ID3v24FrameBody
{

    /**
     * Creates a new FrameBodyTDTG datatype.
     */
    public FrameBodyTDTG()
    {
    }

    public FrameBodyTDTG(FrameBodyTDTG body)
    {
        super(body);
    }

    /**
     * Creates a new FrameBodyTDTG datatype.
     *
     * @param textEncoding
     * @param text
     */
    public FrameBodyTDTG(byte textEncoding, String text)
    {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTDTG datatype.
     *
     * @throws java.io.IOException
     * @throws InvalidTagException
     */
    public FrameBodyTDTG(ByteBuffer byteBuffer, int frameSize) throws InvalidTagException
    {
        super(byteBuffer, frameSize);
    }

    /**
     * @return the frmae identifier
     */
    public String getIdentifier()
    {
        return ID3v24Frames.FRAME_ID_TAGGING_TIME;
    }


}
