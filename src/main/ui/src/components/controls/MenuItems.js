import React from "react";
import MenuItem from "@material-ui/core/MenuItem";

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
