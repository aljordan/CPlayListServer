/*
 * Entagged Audio Tag library
 * Copyright (c) 2003-2005 Christian Laireiter <liree@web.de>
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *  
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.alansaudiotagger.audio.generic;

import org.alansaudiotagger.audio.AudioFile;
import org.alansaudiotagger.audio.exceptions.ModifyVetoException;

import java.io.File;

/**
 * Adapter for
 * {@link org.alansaudiotagger.audio.generic.AudioFileModificationListener}.
 *
 * @author Christian Laireiter
 */
public class AudioFileModificationAdapter implements AudioFileModificationListener
{

    /**
     * (overridden)
     *
     * @see org.alansaudiotagger.audio.generic.AudioFileModificationListener#fileModified(org.alansaudiotagger.audio.AudioFile,
     *File)
     */
    public void fileModified(AudioFile original, File temporary) throws ModifyVetoException
    {
        // Nothing to do
    }

    /**
     * (overridden)
     *
     * @see org.alansaudiotagger.audio.generic.AudioFileModificationListener#fileOperationFinished(File)
     */
    public void fileOperationFinished(File result)
    {
        // Nothing to do
    }

    /**
     * (overridden)
     *
     * @see org.alansaudiotagger.audio.generic.AudioFileModificationListener#fileWillBeModified(org.alansaudiotagger.audio.AudioFile,
     *boolean)
     */
    public void fileWillBeModified(AudioFile file, boolean delete) throws ModifyVetoException
    {
        // Nothing to do
    }

    /**
     * (overridden)
     *
     * @see org.alansaudiotagger.audio.generic.AudioFileModificationListener#vetoThrown(org.alansaudiotagger.audio.generic.AudioFileModificationListener,
     *org.alansaudiotagger.audio.AudioFile,
     *org.alansaudiotagger.audio.exceptions.ModifyVetoException)
     */
    public void vetoThrown(AudioFileModificationListener cause, AudioFile original, ModifyVetoException veto)
    {
        // Nothing to do
    }

}
