/*
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
 */
package org.alansaudiotagger.tag.id3.framebody;

import org.alansaudiotagger.tag.InvalidTagException;
import org.alansaudiotagger.tag.reference.Languages;
import org.alansaudiotagger.tag.reference.MusicalKey;
import org.alansaudiotagger.tag.id3.ID3v24Frames;

import java.nio.ByteBuffer;

/**
 * Initial key Text information frame.
 * <p>The 'Initial key' frame contains the musical key in which the sound starts. It is represented as a string with a maximum length of three characters. The ground keys are represented with "A","B","C","D","E", "F" and "G" and halfkeys represented with "b" and "#". Minor is represented as "m". Example "Cbm". Off key is represented with an "o" only.
 * <p/>
 * <p>For more details, please refer to the ID3 specifications:
 * <ul>
 * <li><a href="http://www.id3.org/id3v2.3.0.txt">ID3 v2.3.0 Spec</a>
 * </ul>
 *
 * @author : Paul Taylor
 * @author : Eric Farng
 * @version $Id: FrameBodyTKEY.java,v 1.12 2008/12/05 11:10:17 paultaylor Exp $
 */
public class FrameBodyTKEY extends AbstractFrameBodyTextInfo implements ID3v24FrameBody, ID3v23FrameBody
{
    /**
     * Creates a new FrameBodyTKEY datatype.
     */
    public FrameBodyTKEY()
    {
    }

    public FrameBodyTKEY(FrameBodyTKEY body)
    {
        super(body);
    }

    /**
     * Creates a new FrameBodyTKEY datatype.
     *
     * @param textEncoding
     * @param text
     */
    public FrameBodyTKEY(byte textEncoding, String text)
    {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTKEY datatype.
     *
     * @throws java.io.IOException
     * @throws InvalidTagException
     */
    public FrameBodyTKEY(ByteBuffer byteBuffer, int frameSize) throws InvalidTagException
    {
        super(byteBuffer, frameSize);
    }

    /**
     * The ID3v2 frame identifier
     *
     * @return the ID3v2 frame identifier  for this frame type
     */
    public String getIdentifier()
    {
        return ID3v24Frames.FRAME_ID_INITIAL_KEY;
    }

    /**
     *
     * @return true if text value is valid language code
     */
    public boolean isValid()
    {
        return MusicalKey.isValid(getFirstTextValue());
    }
}