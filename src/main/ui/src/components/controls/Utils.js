/**
 * @file This file contains additional functions
 * @author Tom Kollmer 
 */

import Submit from "../backend/Submit";

/**
 * Is always called if user input should be added to the value object
 * @param {object} e origin of the function call 
 * @param {object} valueHook access to the user input
 */
function handleInputChange(e, valueHook) {
  const [values, setValues] = valueHook;
  setValues({ ...values, [e.target.name]: e.target.value });
}

/**
 * Is called when the user presses the submit button
 * @param {object} e origin of the function call 
 * @param {object} valueHook access to the user input
 * @param {object} selected_components contains all selected components
 * @param {func} setErrors is called to change the values inside the error object
 * @param {object} history manages the current page and all previous pages
 */
function handleSubmit(e, valueHook, selected_components, setErrors, history) {
  const values = valueHook[0]

  Submit(
    "/policy/InformPolicyForm",
    values,
    selected_components,
    setErrors,
    history,
    e
  );
}

/**
 * Is called when a menu should stay open so that a user can access the menu items
 * @param {object} e origin of the function call 
 * @param {func} setAnchorEl is called when an anchor should be place 
 */
function handleClick(e, setAnchorEl) {
  setAnchorEl(e.currentTarget);
};

/**
 * Is called to close the menu drop down view
 * @param {func} setAnchorEl 
 */
function handleClose(setAnchorEl){
  setAnchorEl(null);
};

/**
 * is called to reset all selected components
 * @param {func} setSelectedComponents is called to change the values inside the selected components object
 * @param {object} selected_components contains all selected components
 */
function resetStates(setSelectedComponents, selected_components) {
  setSelectedComponents({ ...selected_components });
};

export { handleInputChange, handleSubmit,handleClick, handleClose, resetStates };
