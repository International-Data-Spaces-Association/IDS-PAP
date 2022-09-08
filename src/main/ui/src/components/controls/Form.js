/**
 * @file This file contains components that can be used by the pages to show components
 * @author Tom Kollmer 
 */
import React from 'react'
import { useStyle } from "../Style";

/**
 * Basic component for the pages
 * @component
 * @param {object} props additional flags and settings for the form component
 * @returns component
 */
export default function Form(props) {
    const classes = useStyle();
    const { children, ...other } = props;
    return (
        <form className={classes.root} autoComplete="off" {...other}>
          {props.children}
        </form>
      );
}

