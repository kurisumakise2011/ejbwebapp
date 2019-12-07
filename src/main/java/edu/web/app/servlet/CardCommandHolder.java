package edu.web.app.servlet;

import javax.ejb.Local;

@Local
public interface CardCommandHolder {

   CardCommandHolderImpl.AtmOperation atmOperation();

   CardCommandHolderImpl.GetCards getCards();

   CardCommandHolderImpl.FetchCardByNumber fetchCardByNumber();

   CardCommandHolderImpl.CreateCard createCard();

   CardCommandHolderImpl.RemoveCard removeCard();

}
