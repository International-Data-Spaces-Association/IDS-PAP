import axios from "axios";
const BASE_URL = "http://localhost:9090";

function getAllPolicies(data, setData) {
  axios.get(BASE_URL + "/policies").then(
    (response) => {
      setData(response.data);
    },
    (error) => {
      console.log(error);
    }
  );
}

function exportPolicy(id, fileName, contentType) {
  axios.get(BASE_URL + `/policies/export_${id}`).then(
    (response) => {
      const a = document.createElement("a");
      const file = new Blob([JSON.stringify(response.data, null, 2)], { type: contentType });
      a.href = URL.createObjectURL(file);
      a.download = fileName;
      a.click();
    },
    (error) => {
      console.log(error);
    }
  );
}

function deletePolicy(id, data, setData) {
  axios.delete(BASE_URL + `/policies/${id}`).then(
    (response) => {
      getAllPolicies(data, setData);
    },
    (error) => {
      console.log(error);
    }
  );
}

export { getAllPolicies, exportPolicy, deletePolicy };
