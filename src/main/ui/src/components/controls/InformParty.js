import React from "react";
import { Grid } from "@material-ui/core";
import { log_level_list } from "./InitialFieldListValues";
import Input from "./Input";
import ItemPicker from "./ItemPicker";
import Title from "./Title";

export default function InformParty(props) {
    const { handleInputChange, values, errors, xs = 12, sm = 12, md = 12, type="" } = props;
    return (
        <>
            <Grid item xs={xs} sm={sm} md={md}>
                <Grid container>
                    <ItemPicker
                        name= {type + "notificationLevel"}
                        defaultValue=""
                        label="Notification Level"
                        ItemList={log_level_list}
                        onChange={handleInputChange}
                        error={errors[type + "notificationLevel"]}
                        />
                </Grid>

                <Grid container>
                    <Input
                        name= {type + "informedParty"}
                        label="Informed Party (by default, you are the party who gets the notification)"
                        value={values[type + "informedParty"]}
                        placeholder="My Party"
                        onChange={handleInputChange}
                        error={errors[type + "informedParty"]}
                    />
                </Grid>
            </Grid>
        </>
    );
}