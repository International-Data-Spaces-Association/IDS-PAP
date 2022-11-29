/**
 * @file In this file is the template dialog defined 
 * @author Tom Kollmer 
 */

import React, { useState } from "react";
import { useStyle } from "../Style";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import { Button, TextField } from "@material-ui/core";
import { handleInputChange } from "../controls/Utils";

/**
 * Components for the template dialog box
 * @component
 * @param {object} valueHook access to the user input
 * @param {func} handleSubmit is called when the user presses the button to submit the form
 * @param {string} originPath url of the current page
 * @returns component
 */
export default function TemplateDialog(props) {
  const { valueHook, handleSubmit, originPath} = props;
  const [values, setValues] = valueHook;

  const classes = useStyle();
  const [openDialog, setOpenDialog] = useState(false);
  const handleClickOpen = () => {
    setOpenDialog(true);
  };

  const handleClose = () => {
    setOpenDialog(false);
  };

  const handleSave = () => {
    var items = values;
    items["is_template"] = true;
    items["originQuery"] = originPath;

    setValues({ ...items })
    setOpenDialog(false);
    handleSubmit();
  };

  return (
    <>
      <Button
        variant="contained"
        color="secondary"
        className={classes.saveBtn}
        prefix="submit"
        id="Template"
        onClick={handleClickOpen}
      >
        Save as template
      </Button>
      <Dialog open={openDialog} onClose={handleClose}>
        <DialogTitle>Save as Template</DialogTitle>
        <DialogContent>
          <DialogContentText>
            To save the policy as a template, please enter a name and a comment
            here.
          </DialogContentText>
          <TextField
            name="name"
            autoFocus
            margin="dense"
            id="name"
            label="Name"
            type="name"
            fullWidth
            variant="outlined"
            onChange={(e) => {
              handleInputChange(e, valueHook);
            }}
            InputLabelProps={{ shrink: true }}
          />
          <TextField
            name="comment"
            autoFocus
            margin="dense"
            id="name"
            label="Comment"
            type="comment"
            fullWidth
            multiline
            variant="outlined"
            onChange={(e) => {
              handleInputChange(e, valueHook);
            }}
            InputLabelProps={{ shrink: true }}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleSave} color="primary">
            Save
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
