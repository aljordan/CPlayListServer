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
import java.util.Enumeration;
import java.util.Vector;

/**
 * This class multicasts the events to multiple listener instances.<br>
 * Additionally the Vetos are handled. (other listeners are notified).
 *
 * @author Christian Laireiter
 */
public class ModificationHandler implements AudioFileModificationListener
{

    /**
     * The listeners to wich events are broadcasted are stored here.
     */
    private Vector<AudioFileModificationListener> listeners = new Vector<AudioFileModificationListener>();

    /**
     * This method adds an {@link AudioFileModificationListener}
     *
     * @param l Listener to add.
     */
    public void addAudioFileModificationListener(AudioFileModificationListener l)
    {
        if (!this.listeners.contains(l))
        {
            this.listeners.add(l);
        }
    }

    /**
     * (overridden)
     *
     * @see org.alansaudiotagger.audio.generic.AudioFileModificationListener#fileModified(org.alansaudiotagger.audio.AudioFile,
     *File)
     */
    public void fileModified(AudioFile original, File temporary) throws ModifyVetoException
    {
        Enumeration<AudioFileModificationListener> enumer = this.listeners.elements();
        while (enumer.hasMoreElements())
        {
            AudioFileModificationListener current = enumer.nextElement();
            try
            {
                current.fileModified(original, temporary);
            }
            catch (ModifyVetoException e)
            {
                vetoThrown(current, original, e);
                throw e;
            }
        }
    }

    /**
     * (overridden)
     *
     * @see org.alansaudiotagger.audio.generic.AudioFileModificationListener#fileOperationFinished(File)
     */
    public void fileOperationFinished(File result)
    {
        Enumeration<AudioFileModificationListener> enumer = this.listeners.elements();
        while (enumer.hasMoreElements())
        {
            AudioFileModificationListener current = enumer.nextElement();
            current.fileOperationFinished(result);
        }
    }

    /**
     * (overridden)
     *
     * @see org.alansaudiotagger.audio.generic.AudioFileModificationListener#fileWillBeModified(org.alansaudiotagger.audio.AudioFile,
     *boolean)
     */
    public void fileWillBeModified(AudioFile file, boolean delete) throws ModifyVetoException
    {
        Enumeration<AudioFileModificationListener> enumer = this.listeners.elements();
        while (enumer.hasMoreElements())
        {
            AudioFileModificationListener current = enumer.nextElement();
            try
            {
                current.fileWillBeModified(file, delete);
            }
            catch (ModifyVetoException e)
            {
                vetoThrown(current, file, e);
                throw e;
            }
        }
    }

    /**
     * This method removes an {@link AudioFileModificationListener}
     *
     * @param l Listener to remove.
     */
    public void removeAudioFileModificationListener(AudioFileModificationListener l)
    {
        if (this.listeners.contains(l))
        {
            this.listeners.remove(l);
        }
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
        Enumeration<AudioFileModificationListener> enumer = this.listeners.elements();
        while (enumer.hasMoreElements())
        {
            AudioFileModificationListener current = enumer.nextElement();
            current.vetoThrown(cause, original, veto);
        }
    }
}
