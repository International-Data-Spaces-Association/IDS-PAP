import React from 'react'
import { useStyle } from "../Style";

export default function Form(props) {
    const classes = useStyle();
    const { children, ...other } = props;
    return (
        <form className={classes.root} autoComplete="off" {...other}>
          {props.children}
        </form>
      );
}

