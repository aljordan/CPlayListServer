/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cplaylistserver;

/**
 *
 * @author Alan Jordan
 */
public class CPlayControl {
  public enum CPlayAction {
      PAUSE_SONG, NEXT_SONG, PREVIOUS_SONG, FAST_FORWARD, REWIND,
      CHANGE_PHASE, VOLUME_UP, VOLUME_DOWN
  }
}
