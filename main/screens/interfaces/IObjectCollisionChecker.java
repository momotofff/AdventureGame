package main.screens.interfaces;

import entity.Entity;
import objects.BaseObject;

public interface IObjectCollisionChecker
{
    public BaseObject checkObject(Entity player);
}
