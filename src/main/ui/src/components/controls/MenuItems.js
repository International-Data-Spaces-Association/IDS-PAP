/**
 * @file This contains the menu item component 
 * @author Tom Kollmer 
 */
import React from "react";
import MenuItem from "@material-ui/core/MenuItem";

/**
 * Components for the delete data pages
 * @component
 * @param {object} selectedComponents that contains all selected components
 * @param {object} setAnchorEl contains information about the position of a menu drop down menu
 * @returns component
 */
export default function MenuItems(props) {
  const { selectedComponents, setAnchorEl } = props;

  const dict = selectedComponents.availableComponents;

  const items = dict.map((item) => (
    (!item.isVisible ? (
        <MenuItem
        key={item.id}
        onClick={() => {
            item.isVisible = true;
            selectedComponents.order.push(item.id);
            setAnchorEl(null);
        }}
        id={item.id}
      >
        Restrict {item.name}
      </MenuItem>
      ) : null)
  ));
  return items;
}
