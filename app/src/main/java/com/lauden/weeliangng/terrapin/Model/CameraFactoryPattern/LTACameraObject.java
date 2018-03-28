package com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern;

/**
 * Created by WeeLiang Ng on 19/8/2017.
 */
public class LTACameraObject extends CameraStrategy implements Comparable<LTACameraObject> {

    private String timestamp;
    private String image;
  private double lat;

    private double lon;
    private String camera_id;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name;


    public LTACameraObject() {

    }
    public LTACameraObject(String camId, String timing, String imagehtml) {
        camera_id = camId;
        timestamp = timing;

        image = imagehtml;

    }

    public LTACameraObject(String camId)
    {
        super();
        this.camera_id = camId;
    }

    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(String camera_id) {
        this.camera_id = camera_id;
    }

    /*
 if (!this.make.equalsIgnoreCase(other.make))
            return this.make.compareTo(other.make);
        if (!this.model.equalsIgnoreCase(other.model))
            return this.model.compareTo(other.model);
        return this.year - other.year;
*/

    @Override
    public int compareTo(LTACameraObject other)
    {
       // int compareCamera_id = ((LTACameraObject) compareCameraObject).getCamera_id();
        //if (!this.getCamera_id().equalsIgnoreCase(other.camera_id))
           // return this.getCamera_id().compareTo(other.camera_id);

        //Ascending order
        //return this.camera_id - compareCamera_id;

        //Descending order
      //  return compareCamera_id - this.camera_id;
        return getCamera_id().compareTo(other.getCamera_id());
       // return this.getName().compareTo(emp.getName());
    }

}
