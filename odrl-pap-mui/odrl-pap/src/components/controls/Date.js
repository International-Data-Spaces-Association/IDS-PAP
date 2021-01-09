import React from 'react'
import {TextField} from "@material-ui/core";

export default function Date(props) {
    const {name, label, value, error=null, onChange} = props;
    return (
        <TextField
            label={label}
            name={name}
            value={value}
            type="datetime-local"
            variant="outlined"
            InputLabelProps={{ shrink: true }}
            onChange={onChange}
            {...(error && {error:true, helperText:error})}
        />
    )
}

