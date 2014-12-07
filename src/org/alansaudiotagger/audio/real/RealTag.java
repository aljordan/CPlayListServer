package org.alansaudiotagger.audio.real;

import org.alansaudiotagger.audio.generic.GenericTag;
import org.alansaudiotagger.tag.TagField;
import org.alansaudiotagger.tag.TagFieldKey;
import org.alansaudiotagger.tag.KeyNotFoundException;
import org.alansaudiotagger.tag.FieldDataInvalidException;

public class RealTag extends GenericTag
{
    public String toString()
    {
        String output = "REAL " + super.toString();
        return output;
    }


}
