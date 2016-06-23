package dao;

import org.springframework.stereotype.Service;

import model.Project;

@Service
public class DaoImpl implements Dao  {


	public String getName()
	{
		Project project =new Project("hjello");
		return project.projectName;
	}
}
