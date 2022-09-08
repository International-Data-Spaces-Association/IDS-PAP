/**
 * @file This file contains components that are used by the inform party page
 * @author Tom Kollmer 
 */
import React from "react";
import { Grid } from "@material-ui/core";
import { log_level_list } from "./InitialFieldListValues";
import Input from "./Input";
import ItemPicker from "./ItemPicker";

/**
 * Function that defines the components used by the inform party page.
 * @component
 * @param {object} valueHook access to the user input
 * @param {object} errors contains all error messages
 * @param {number} xs size of the component at small screens
 * @param {number} sm size of the component at medium screens
 * @param {number} md size of the component at large screens
 * @param {number} prefix is added before all component names
 * @returns component
 */
export default function InformParty(props) {
    const {valueHook, errors, xs = 12, sm = 12, md = 12, prefix="" } = props;
    return (
        <>
            <Grid item xs={xs} sm={sm} md={md}>
                <Grid container>
                    <ItemPicker
                        name= {prefix + "notificationLevel"}
                        defaultValue=""
                        label="Notification Level"
                        ItemList={log_level_list}
                        valueHook={valueHook}
                        errors={errors}
                        />
                </Grid>

                <Grid container>
                    <Input
                        name= {prefix + "informedParty"}
                        label="Informed Party (by default, you are the party who gets the notification)"
                        placeholder="My Party"
                        valueHook={valueHook}
                        errors={errors}
                    />
                </Grid>
            </Grid>
        </>
    );
}