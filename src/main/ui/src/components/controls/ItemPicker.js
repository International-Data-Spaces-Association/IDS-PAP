import React from "react";
import { TextField, MenuItem, Grid } from "@material-ui/core";

export default function ItemPicker(props) {
  const { name, label, value, defaultValue, ItemList, error = null, onChange, xs=11, sm=11, md=11} = props;
  return (
    <>
      <Grid item xs={xs} sm={sm} md={md}>
      <TextField
        select
        name={name}
        label={label}
        value={value}
        defaultValue={defaultValue}
        variant="outlined"
        onChange={onChange}
        InputLabelProps={{
          shrink: true,
        }}
        {...(error && { error: true, helperText: error })}
      >
        {ItemList.map((option) => (
          <MenuItem key={option.value} value={option.value}>
            {option.id}
          </MenuItem>
        ))}
      </TextField>
      </Grid>
    </>
  );
}
