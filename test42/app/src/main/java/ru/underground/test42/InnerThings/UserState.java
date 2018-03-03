package ru.underground.test42.InnerThings;

public class UserState extends Demands {

    public void setIngr(int id, int size) {
        int i = 0;
        while(m_ingredients.get(i).ingredient.getId()!=id) {
            i++;
        }
        if(i<m_ingredients.size())
            m_ingredients.get(i).size = size;
    }

    public void addIngr(int id, int size) {
        int i = 0;
        while(m_ingredients.get(i).ingredient.getId()!=id) {
            i++;
        }
        if(i<m_ingredients.size())
            m_ingredients.get(i).size += size;
        else {
            CurrentIngredient ingr = new CurrentIngredient();
            ingr.ingredient = IngridientManager.getIngredient(id);
            ingr.size = size;
            if(ingr.ingredient!=null)
                m_ingredients.add(ingr);
        }
    }

    public void delIngr(int id)
    {
        int i = 0;
        while(m_ingredients.get(i).ingredient.getId()!=id) {
            i++;
        }
        if(i<m_ingredients.size())
            m_ingredients.remove(i);
    }

    public void addTool(int id) {
        int i = 0;
        while(m_tools.get(i).getId()!=id) {
            i++;
        }
        if(i>=m_tools.size())
        {
            Tool tool = ToolManager.getTool(id);
            if(tool!=null)
                m_tools.add(tool);
        }
    }

    public void delTool(int id)
    {
        int i = 0;
        while(m_tools.get(i).getId()!=id) {
            i++;
        }
        if(i<m_tools.size())
            m_tools.remove(i);
    }


}
