package com.lauden.weeliangng.terrapin.Model.IncidentDAOpattern;

import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;

import java.util.List;

/**
 * Created by WeeLiang Ng on 26/2/2018.
 */

public interface IncidentDAO {

    public List<IncidentObject> getAllIncidents();
    public IncidentObject getIncidentsObject(int id) ;
    public void UpdateIncidentsObject(IncidentObject cmObj);
    public void UpdateIncidentObjectList();
    public void DeleteincidentObject(IncidentObject cmObj);
    public void DeleteIncidentObjectList();


}