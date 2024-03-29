package org.alansaudiotagger.audio.real;

import org.alansaudiotagger.audio.exceptions.CannotReadException;
import org.alansaudiotagger.audio.generic.AudioFileReader;
import org.alansaudiotagger.audio.generic.GenericAudioHeader;
import org.alansaudiotagger.audio.generic.GenericTag;
import org.alansaudiotagger.audio.generic.Utils;
import org.alansaudiotagger.tag.Tag;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Real Media File Format: Major Chunks: .RMF PROP MDPR CONT DATA INDX
 */
public class RealFileReader extends AudioFileReader
{

    @Override
    protected GenericAudioHeader getEncodingInfo(RandomAccessFile raf) throws CannotReadException, IOException
    {
        final GenericAudioHeader rv = new GenericAudioHeader();
        final RealChunk prop = findPropChunk(raf);
        final DataInputStream dis = prop.getDataInputStream();
        final int objVersion = Utils.readUint16(dis);
        if (objVersion == 0)
        {
            final long maxBitRate = Utils.readUint32(dis) / 1000;
            final long avgBitRate = Utils.readUint32(dis) / 1000;
            final long maxPacketSize = Utils.readUint32(dis);
            final long avgPacketSize = Utils.readUint32(dis);
            final long packetCnt = Utils.readUint32(dis);
            final int duration = Utils.readUint32AsInt(dis) / 1000;
            final long preroll = Utils.readUint32(dis);
            final long indexOffset = Utils.readUint32(dis);
            final long dataOffset = Utils.readUint32(dis);
            final int numStreams = Utils.readUint16(dis);
            final int flags = Utils.readUint16(dis);
            rv.setBitrate((int) avgBitRate);
            rv.setLength(duration);
            rv.setVariableBitRate(maxBitRate != avgBitRate);
        }
        return rv;
    }

    private RealChunk findPropChunk(RandomAccessFile raf) throws IOException, CannotReadException
    {
        final RealChunk rmf = RealChunk.readChunk(raf);
        final RealChunk prop = RealChunk.readChunk(raf);
        return prop;
    }

    private RealChunk findContChunk(RandomAccessFile raf) throws IOException, CannotReadException
    {
        final RealChunk rmf = RealChunk.readChunk(raf);
        final RealChunk prop = RealChunk.readChunk(raf);
        RealChunk rv = RealChunk.readChunk(raf);
        while (!rv.isCONT()) rv = RealChunk.readChunk(raf);
        return rv;
    }

    @Override
    protected Tag getTag(RandomAccessFile raf) throws CannotReadException, IOException
    {
        final RealChunk cont = findContChunk(raf);
        final DataInputStream dis = cont.getDataInputStream();
        final String title = Utils.readString(dis, Utils.readUint16(dis));
        final String author = Utils.readString(dis, Utils.readUint16(dis));
        final String copyright = Utils.readString(dis, Utils.readUint16(dis));
        final String comment = Utils.readString(dis, Utils.readUint16(dis));
        final RealTag rv = new RealTag();
        // NOTE: frequently these fields are off-by-one, thus the crazy
        // logic below...
        rv.addTitle(title.length() == 0 ? author : title);
        rv.addArtist(title.length() == 0 ? copyright : author);
        rv.addComment(comment);
        return rv;
    }

}

